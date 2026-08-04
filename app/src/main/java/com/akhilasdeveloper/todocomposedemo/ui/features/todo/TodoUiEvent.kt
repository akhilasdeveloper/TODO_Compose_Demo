package com.akhilasdeveloper.todocomposedemo.ui.features.todo

sealed interface TodoUiEvent {
    data class ShowMessage(val message: String): TodoUiEvent
}