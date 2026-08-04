package com.akhilasdeveloper.todocomposedemo.ui.features.todo

import androidx.compose.runtime.Immutable
import com.akhilasdeveloper.todocomposedemo.data.db.entities.TodoEntity

@Immutable
data class TodoUiState(
    val showInputDialog: Boolean = false,
    val editRequest: TodoEntity? = null,
    val isSelection: Boolean = false,
    val showLoader: Boolean = false,
    val todos: List<TodoEntity> = listOf(),
    val selectedTodos: Set<TodoEntity> = setOf()
)
