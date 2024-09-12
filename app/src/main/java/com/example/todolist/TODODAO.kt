package com.example.todolist

import androidx.annotation.RequiresPermission
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TODODAO {
    @Insert
    fun addTodo(todo: TODO)

    @Delete
    fun deleteTodo(todo: TODO)

    @Update
    fun editTodo(todo: TODO)

//    flow is similar to livedata
    @Query("SELECT * FROM TODO ORDER BY Priority ASC")
    fun todoPriority(): Flow<List<TODO>>

    @Query("SELECT * FROM TODO ORDER BY dueDate ASC")
    fun todoDueDate(): Flow<List<TODO>>

    @Query("SELECT * FROM TODO ORDER BY isCompleted ASC")
    fun todoCompleted(): Flow<List<TODO>>

}