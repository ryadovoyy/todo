package com.ryadovoy.todo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ryadovoy.todo.data.*
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TaskRepository()
    private val _tasks = MutableLiveData<List<Task>>()
    private val _error = MutableLiveData<String>()

    val tasks: LiveData<List<Task>> get() = _tasks
    val error: LiveData<String> get() = _error

    fun getTasks() {
        viewModelScope.launch {
            when (val response = repository.getTasks()) {
                is ApiResponse.Success -> _tasks.value = response.data
                is ApiResponse.Error -> _error.value = "Failed to load tasks: ${response.exception.message}"
            }
        }
    }

    fun addTask(task: TaskCreationRequest) {
        viewModelScope.launch {
            when (val response = repository.addTask(task)) {
                is ApiResponse.Success -> {
                    val updatedList = createMutableTaskList()
                    updatedList.add(response.data)
                    _tasks.value = updatedList
                }
                is ApiResponse.Error -> _error.value = "Failed to add task: ${response.exception.message}"
            }
        }
    }

    fun updateTask(id: Long, task: TaskUpdateRequest) {
        viewModelScope.launch {
            when (val response = repository.updateTask(id, task)) {
                is ApiResponse.Success -> {
                    val updatedList = createMutableTaskList()
                    val index = updatedList.indexOfFirst { it.id == id }
                    if (index != -1) {
                        updatedList[index] = response.data
                        _tasks.value = updatedList
                    }
                }
                is ApiResponse.Error -> _error.value = "Failed to update task: ${response.exception.message}"
            }
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            when (val response = repository.deleteTask(id)) {
                is ApiResponse.Success -> {
                    val updatedList = createMutableTaskList()
                    val index = updatedList.indexOfFirst { it.id == id }
                    if (index != -1) {
                        updatedList.removeAt(index)
                        _tasks.value = updatedList
                    }
                }
                is ApiResponse.Error -> _error.value = "Failed to delete task: ${response.exception.message}"
            }
        }
    }

    private fun createMutableTaskList(): MutableList<Task> {
        return _tasks.value.orEmpty().toMutableList()
    }
}
