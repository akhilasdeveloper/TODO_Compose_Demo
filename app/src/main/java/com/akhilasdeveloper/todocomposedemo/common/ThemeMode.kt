package com.akhilasdeveloper.todocomposedemo.common

sealed class ThemeMode(val value: Int) {
    data object Fun : ThemeMode(0)
    data object Classic : ThemeMode(1)

    companion object {
        fun parse(value: Int?) = when (value) {
            Fun.value -> Fun
            Classic.value -> Classic
            else -> Fun
        }
    }
}