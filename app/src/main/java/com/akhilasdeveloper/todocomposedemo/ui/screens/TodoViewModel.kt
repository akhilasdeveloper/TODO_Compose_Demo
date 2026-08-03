package com.akhilasdeveloper.todocomposedemo.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhilasdeveloper.todocomposedemo.common.Resource
import com.akhilasdeveloper.todocomposedemo.db.entities.TodoEntity
import com.akhilasdeveloper.todocomposedemo.repositories.TodoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class TodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(TodoViewState())
    val viewState = _viewState.asStateFlow()

    private val _viewEvent = MutableSharedFlow<TodoViewEvent>()
    val viewEvent = _viewEvent.asSharedFlow()

    fun performAction(action: TodoAction){

        when(action){
            TodoAction.HideAddDialog -> {
                _viewState.update { it.copy(showInputDialog = false) }
            }
            TodoAction.HideEditDialog -> {
                _viewState.update { it.copy(editRequest = null) }
            }
            TodoAction.ShowAddDialog -> {
                _viewState.update { it.copy(showInputDialog = true) }
            }
            is TodoAction.ShowEditDialog -> {
                _viewState.update { it.copy(editRequest = action.todo) }
            }

            TodoAction.ClearSelection -> {
                _viewState.update { it.copy(selectedTodos = setOf(), isSelection = false) }
            }

            is TodoAction.AddNewTodo -> {
                viewModelScope.launch {
                    val response = repository.insertTodo(TodoEntity(text = action.text))
                    handleErrorResponse(response)
                    showSuccessMessage(response)
                }
            }
            is TodoAction.DeleteTodo -> {
                viewModelScope.launch {
                    val response = repository.deleteTodo(action.todo)
                    handleErrorResponse(response)
                    showSuccessMessage(response)
                }
            }
            is TodoAction.UpdateTodo -> {
                viewModelScope.launch {
                    val response = repository.updateTodo(action.todo.copy(text = action.text))
                    handleErrorResponse(response)
                    showSuccessMessage(response)
                }
            }

            is TodoAction.PerformClick -> {
                if (_viewState.value.isSelection) {
                    _viewState.update {
                        if (it.selectedTodos.contains(action.todo)){
                            it.copy(selectedTodos = it.selectedTodos - action.todo)
                        } else {
                            it.copy(selectedTodos = it.selectedTodos + action.todo)
                        }
                    }
                } else {
                    viewModelScope.launch {
                        val response = repository.updateTodo(action.todo.copy(isDone = !action.todo.isDone))
                        handleErrorResponse(response)
                    }
                }
            }

            is TodoAction.StartSelection -> {
                _viewState.update {
                    it.copy(
                        isSelection = true,
                        selectedTodos = it.selectedTodos + action.todo
                    )
                }
            }

            TodoAction.DeleteSelection -> {
                viewModelScope.launch {
                    val response = repository.deleteTodos(_viewState.value.selectedTodos.toList())
                    handleErrorResponse(response)
                    showSuccessMessage(response)
                    performAction(TodoAction.ClearSelection)
                }
            }
            TodoAction.MarkSelectionCompleted -> {
                viewModelScope.launch {
                    val response = repository.updateTodos(_viewState.value.selectedTodos.map { it.copy(isDone = true) })
                    handleErrorResponse(response)
                    showSuccessMessage(response)
                    performAction(TodoAction.ClearSelection)
                }
            }
            TodoAction.MarkSelectionIncompleted -> {
                viewModelScope.launch {
                    val response = repository.updateTodos(_viewState.value.selectedTodos.map { it.copy(isDone = false) })
                    handleErrorResponse(response)
                    showSuccessMessage(response)
                    performAction(TodoAction.ClearSelection)
                }
            }

            TodoAction.FetchTodos -> {
                viewModelScope.launch {
                    _viewState.update { it.copy(showLoader = true) }
                    repository.getTodos().collectLatest {resource ->
                        when(resource){
                            is Resource.Error -> handleErrorResponse(resource)
                            is Resource.Success<List<TodoEntity>> -> {
                                _viewState.update { it.copy(todos = resource.data, showLoader = false) }
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun <T>handleErrorResponse(response: Resource<T>){
        when(response){
            is Resource.Error -> {
                _viewEvent.emit(TodoViewEvent.ShowMessage(response.message?:"Unknown error"))
                _viewState.update { it.copy(showLoader = false) }
            }
            else -> {}
        }
    }

    private suspend fun <T>showSuccessMessage(response: Resource<T>){
        when(response){
            is Resource.Success -> {
                _viewEvent.emit(TodoViewEvent.ShowMessage("Operation completed"))
                _viewState.update { it.copy(showLoader = false) }
            }
            else -> {}
        }
    }

}