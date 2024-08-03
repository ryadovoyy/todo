package com.ryadovoy.todo.data

class TaskRepository {
    private val taskApi = RetrofitInstance.api

    suspend fun getTasks(): ApiResponse<List<Task>> {
        return safeApiCall { taskApi.getTasks() }
    }

    suspend fun addTask(task: TaskCreationRequest): ApiResponse<Task> {
        return safeApiCall { taskApi.addTask(task) }
    }

    suspend fun updateTask(id: Long, task: TaskUpdateRequest): ApiResponse<Task> {
        return safeApiCall { taskApi.updateTask(id, task) }
    }

    suspend fun deleteTask(id: Long): ApiResponse<Unit> {
        return safeApiCall { taskApi.deleteTask(id) }
    }
}
