package com.akhilasdeveloper.todocomposedemo.di

import androidx.room.Room
import com.akhilasdeveloper.todocomposedemo.db.TodoDatabase
import com.akhilasdeveloper.todocomposedemo.repositories.TodoRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            get(),
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }

    single {
        get<TodoDatabase>().todoDao()
    }

    singleOf(::TodoRepository)
}