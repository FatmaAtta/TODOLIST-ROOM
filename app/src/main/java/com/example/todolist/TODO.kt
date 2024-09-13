package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class TODO(
    var taskName: String,
    var taskDesc: String,
    var Priority: Int,
    var dueDate: LocalDateTime? = null,
    var isCompleted: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
