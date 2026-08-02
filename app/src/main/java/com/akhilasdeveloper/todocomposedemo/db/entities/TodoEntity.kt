package com.akhilasdeveloper.todocomposedemo.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    val text: String,
    val isDone: Boolean = false
)
