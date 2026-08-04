package com.akhilasdeveloper.todocomposedemo.data.repositories

import android.database.sqlite.SQLiteException
import com.akhilasdeveloper.todocomposedemo.common.Resource
import com.akhilasdeveloper.todocomposedemo.data.db.dao.TodoDao
import com.akhilasdeveloper.todocomposedemo.data.db.entities.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val todoDao: TodoDao
): TodoRepository {

    override fun getTodos(): Flow<Resource<List<TodoEntity>>> {
        return todoDao.getAllTodos()
            .map<List<TodoEntity>, Resource<List<TodoEntity>>> { users ->
                Resource.Success(users)
            }.catch { throwable ->
                emit(Resource.Error(throwable, "Failed to load todos from local storage."))
            }
    }

    override suspend fun insertTodo(todoEntity: TodoEntity): Resource<Unit> {
        return safeDbOperation {
            todoDao.insert(todoEntity)
        }
    }

    override suspend fun deleteTodo(todoEntity: TodoEntity): Resource<Unit> {
        return safeDbOperation {
            todoDao.delete(todoEntity)
        }
    }

    override suspend fun deleteTodos(todoEntities: List<TodoEntity>): Resource<Unit> {
        return safeDbOperation {
            todoDao.deleteAll(todoEntities)
        }
    }

    override suspend fun updateTodo(todoEntity: TodoEntity): Resource<Unit> {
        return safeDbOperation {
            todoDao.update(todoEntity)
        }
    }

    override suspend fun updateTodos(todoEntities: List<TodoEntity>): Resource<Unit> {
        return safeDbOperation {
            todoDao.updateAll(todoEntities)
        }
    }

    private suspend fun <T> safeDbOperation(block: suspend ()-> T): Resource<T> {
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