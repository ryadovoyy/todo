package com.ryadovoy.todo.data

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val completed: Boolean
)

data class TaskCreationRequest(
    val title: String,
    val description: String
)

data class TaskUpdateRequest(
    val title: String,
    val description: String,
    val completed: Boolean
)
