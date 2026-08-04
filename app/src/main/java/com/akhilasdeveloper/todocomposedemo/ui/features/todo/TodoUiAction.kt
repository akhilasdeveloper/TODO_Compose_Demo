package com.akhilasdeveloper.todocomposedemo.ui.features.todo

import com.akhilasdeveloper.todocomposedemo.data.db.entities.TodoEntity

sealed interface TodoUiAction {

    data object FetchTodos: TodoUiAction

    data object ShowAddDialog: TodoUiAction
    data object HideAddDialog: TodoUiAction

    data class ShowEditDialog(val todo: TodoEntity): TodoUiAction
    data object HideEditDialog: TodoUiAction

    data class PerformClick(val todo: TodoEntity): TodoUiAction
    data class StartSelection(val todo: TodoEntity): TodoUiAction
    data object ClearSelection: TodoUiAction
    data object DeleteSelection: TodoUiAction

    data object MarkSelectionCompleted: TodoUiAction
    data object MarkSelectionIncompleted: TodoUiAction

    data class AddNewTodo(val text: String): TodoUiAction
    data class DeleteTodo(val todo: TodoEntity): TodoUiAction
    data class UpdateTodo(val text: String, val todo: TodoEntity): TodoUiAction

}