package com.example.todolist

import java.time.LocalDateTime

data class TODOState(
    val TODOs: List<TODO> = emptyList(),
    val taskName: String = "",
    val taskDesc: String = "",
    val Priority: Int = 3,
    val dueDate: LocalDateTime? = null,
    val isCompleted: Boolean = false,
    val isAddingTODO: Boolean = false,
    val sortType: SortType = SortType.NONE,
)
