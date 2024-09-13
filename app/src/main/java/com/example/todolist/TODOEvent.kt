package com.example.todolist

import java.time.LocalDateTime

//contains anything related to user interaction, clicking any button
sealed interface TODOEvent{
    object SaveTODO: TODOEvent
    data class SetTODOName(val TODOName: String): TODOEvent
    data class SetTODODesc(val TODODesc: String): TODOEvent
    data class SetTODOPriority(val TODOPriority: Int): TODOEvent
    data class SetTODODueDate(val TODODueDate: LocalDateTime): TODOEvent
    data class SetTODOIsComp(val TODOIsComp: Boolean): TODOEvent
    data class SortTODO(val sortType: SortType): TODOEvent
    data class DeleteTODO(val TODO: TODO): TODOEvent
    object ShowDialogue: TODOEvent
    object HideDialogue: TODOEvent
}