package com.akhilasdeveloper.todocomposedemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akhilasdeveloper.todocomposedemo.db.dao.TodoDao
import com.akhilasdeveloper.todocomposedemo.db.entities.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}