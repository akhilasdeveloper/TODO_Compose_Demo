package com.akhilasdeveloper.todocomposedemo.data.repositories

import com.akhilasdeveloper.todocomposedemo.common.Resource
import com.akhilasdeveloper.todocomposedemo.data.db.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getTodos(): Flow<Resource<List<TodoEntity>>>

    suspend fun insertTodo(todoEntity: TodoEntity): Resource<Unit>

    suspend fun deleteTodo(todoEntity: TodoEntity): Resource<Unit>

    suspend fun deleteTodos(todoEntities: List<TodoEntity>): Resource<Unit>

    suspend fun updateTodo(todoEntity: TodoEntity): Resource<Unit>

    suspend fun updateTodos(todoEntities: List<TodoEntity>): Resource<Unit>

}