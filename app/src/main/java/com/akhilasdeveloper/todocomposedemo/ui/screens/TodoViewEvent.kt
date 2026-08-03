package com.akhilasdeveloper.todocomposedemo.ui.screens

sealed interface TodoViewEvent {
    data class ShowMessage(val message: String): TodoViewEvent
}