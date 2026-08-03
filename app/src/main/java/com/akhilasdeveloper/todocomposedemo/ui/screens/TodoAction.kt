package com.akhilasdeveloper.todocomposedemo.ui.screens

import com.akhilasdeveloper.todocomposedemo.db.entities.TodoEntity

sealed interface TodoAction {

    data object FetchTodos: TodoAction

    data object ShowAddDialog: TodoAction
    data object HideAddDialog: TodoAction

    data class ShowEditDialog(val todo: TodoEntity): TodoAction
    data object HideEditDialog: TodoAction

    data class PerformClick(val todo: TodoEntity): TodoAction
    data class StartSelection(val todo: TodoEntity): TodoAction
    data object ClearSelection: TodoAction
    data object DeleteSelection: TodoAction

    data object MarkSelectionCompleted: TodoAction
    data object MarkSelectionIncompleted: TodoAction

    data class AddNewTodo(val text: String): TodoAction
    data class DeleteTodo(val todo: TodoEntity): TodoAction
    data class UpdateTodo(val text: String, val todo: TodoEntity): TodoAction

}