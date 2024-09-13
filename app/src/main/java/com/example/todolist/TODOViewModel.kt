package com.example.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TODOViewModel(private val dao: TODODAO): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.NONE)
    private val _todos = _sortType.flatMapLatest {
        sortType -> when(sortType) {
            SortType.NONE -> dao.todoNoSort()
            SortType.PRIORITY -> dao.todoPriority()
            SortType.DUE_DATE -> dao.todoDueDate()
            SortType.IS_COMPLETED -> dao.todoCompleted()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    }
    private val _state = MutableStateFlow(TODOState())
    val state = combine(_state, _sortType, _todos){
        state, sortType, todos ->
        state.copy(
            TODOs = todos,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TODOState())
    fun onEvent(event: TODOEvent){
        when(event){
            is TODOEvent.DeleteTODO -> {
//                we need to execute the deleteTodo in a coroutine because its a suspend function
                viewModelScope.launch {
                    dao.deleteTodo(event.TODO)
                }
            }
            TODOEvent.SaveTODO -> {
                val taskName = state.value.taskName
                val taskDesc = state.value.taskDesc
                val Priority = state.value.Priority
                val dueDate = state.value.dueDate
                val isCompleted = state.value.isCompleted
                if(taskName.isBlank()){
                    return
                }
                val todo = TODO(
                    taskName = taskName,
                    taskDesc = taskDesc,
                    Priority = Priority,
                    dueDate = dueDate,
                    isCompleted = isCompleted,
                )
                viewModelScope.launch {
                    dao.addTodo(todo)
                }
                _state.update {
                    it.copy(
                        isAddingTODO = false,
                        taskName = "",
                        taskDesc = "",
                        Priority = 3,
                        dueDate = null,
                        isCompleted = false,
                    )
                }
            }
            is TODOEvent.SetTODODesc -> {
                _state.update {
                    it.copy(
                        taskDesc = event.TODODesc
                    )
                }
            }
            is TODOEvent.SetTODODueDate -> {
                _state.update {
                    it.copy(
                        dueDate = event.TODODueDate
                    )
                }
            }
            is TODOEvent.SetTODOIsComp -> {
                _state.update {
                    it.copy(
                        isCompleted = true
                    )
                }
            }
            is TODOEvent.SetTODOName -> {
                _state.update {
                    it.copy(
                        taskName = event.TODOName
                    )
                }
            }
            is TODOEvent.SetTODOPriority -> {
                _state.update {
                    it.copy(
                        Priority = event.TODOPriority
                    )
                }
            }
            is TODOEvent.SortTODO -> {
                _sortType.value = event.sortType
            }
            TODOEvent.ShowDialogue -> {
                _state.update {
                    it.copy(
                        isAddingTODO = true
                    )
                }
            }
            TODOEvent.HideDialogue -> {
                _state.update {
                    it.copy(
                        isAddingTODO = false
                    )
                }
            }
        }
    }

}