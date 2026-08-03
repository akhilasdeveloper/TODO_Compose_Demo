package com.akhilasdeveloper.todocomposedemo.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.akhilasdeveloper.todocomposedemo.db.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY date DESC")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TodoEntity): Long

    @Update
    suspend fun update(entity: TodoEntity)

    @Update
    suspend fun updateAll(entities: List<TodoEntity>)

    @Delete
    suspend fun delete(entity: TodoEntity)

    @Delete
    suspend fun deleteAll(entities: List<TodoEntity>)

}