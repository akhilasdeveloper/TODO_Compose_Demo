package com.akhilasdeveloper.todocomposedemo.ui.screens

import androidx.compose.runtime.Immutable
import com.akhilasdeveloper.todocomposedemo.db.entities.TodoEntity

@Immutable
data class TodoViewState(
    val showInputDialog: Boolean = false,
    val editRequest: TodoEntity? = null,
    val isSelection: Boolean = false,
    val showLoader: Boolean = false,
    val todos: List<TodoEntity> = listOf(),
    val selectedTodos: Set<TodoEntity> = setOf()
)
