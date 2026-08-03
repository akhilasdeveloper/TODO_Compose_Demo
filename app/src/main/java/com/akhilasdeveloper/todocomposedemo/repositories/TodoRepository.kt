package com.akhilasdeveloper.todocomposedemo.repositories

import android.database.sqlite.SQLiteException
import com.akhilasdeveloper.todocomposedemo.common.Resource
import com.akhilasdeveloper.todocomposedemo.db.dao.TodoDao
import com.akhilasdeveloper.todocomposedemo.db.entities.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class TodoRepository(
    private val todoDao: TodoDao
) {

    fun getTodos(): Flow<Resource<List<TodoEntity>>> {
        return todoDao.getAllTodos()
            .map<List<TodoEntity>, Resource<List<TodoEntity>>> { users ->
                Resource.Success(users)
            }.catch { throwable ->
                emit(Resource.Error(throwable, "Failed to load todos from local storage."))
            }
    }

    suspend fun insertTodo(todoEntity: TodoEntity): Resource<Unit> {
        return safeDbOperation {
            todoDao.insert(todoEntity)
        }
    }

    suspend fun deleteTodo(todoEntity: TodoEntity): Resource<Unit> {
        return safeDbOperation {
            todoDao.delete(todoEntity)
        }
    }

    suspend fun deleteTodos(todoEntities: List<TodoEntity>): Resource<Unit> {
        return safeDbOperation {
            todoDao.deleteAll(todoEntities)
        }
    }

    suspend fun updateTodo(todoEntity: TodoEntity): Resource<Unit> {
        return safeDbOperation {
            todoDao.update(todoEntity)
        }
    }

    suspend fun updateTodos(todoEntities: List<TodoEntity>): Resource<Unit> {
        return safeDbOperation {
            todoDao.updateAll(todoEntities)
        }
    }

    suspend fun <T> safeDbOperation(block: suspend ()-> T): Resource<T> {
        return try {
            val data = block()
            Resource.Success(data)
        } catch (e: SQLiteException) {
            Resource.Error(e, "Database error occurred.")
        } catch (e: Exception) {
            Resource.Error(e, "Unexpected error: ${e.localizedMessage}")
        }
    }

}