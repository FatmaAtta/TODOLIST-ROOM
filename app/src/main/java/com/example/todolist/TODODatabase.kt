package com.example.todolist
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TODO::class],
    version = 1
)

abstract class TODODatabase: RoomDatabase() {
    abstract val dao: TODODAO
}