package com.ryadovoy.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ryadovoy.todo.data.Task
import com.ryadovoy.todo.data.TaskCreationRequest
import com.ryadovoy.todo.data.TaskUpdateRequest
import com.ryadovoy.todo.ui.TaskAdapter
import com.ryadovoy.todo.ui.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val addTaskButton: FloatingActionButton = findViewById(R.id.addTaskButton)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskViewModel.tasks.observe(this) { tasks ->
            taskAdapter = TaskAdapter(tasks, taskViewModel, ::showEditTaskDialog)
            recyclerView.adapter = taskAdapter
        }

        taskViewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        addTaskButton.setOnClickListener {
            showAddTaskDialog()
        }

        taskViewModel.getTasks()
    }

    private fun showAddTaskDialog() {
        showTaskDialog(onSave = { title, description, _ ->
            val newTask = TaskCreationRequest(title = title, description = description)
            CoroutineScope(Dispatchers.IO).launch {
                taskViewModel.addTask(newTask)
            }
        })
    }

    private fun showEditTaskDialog(task: Task) {
        showTaskDialog(
            title = task.title,
            description = task.description,
            completed = task.completed,
            isCompletedVisible = true,
            onSave = { title, description, completed ->
                val updatedTask = TaskUpdateRequest(
                    title = title,
                    description = description,
                    completed = completed
                )
                CoroutineScope(Dispatchers.IO).launch {
                    taskViewModel.updateTask(task.id, updatedTask)
                }
            },
            onDelete = {
                CoroutineScope(Dispatchers.IO).launch {
                    taskViewModel.deleteTask(task.id)
                }
            }
        )
    }

    private fun showTaskDialog(
        title: String = "",
        description: String = "",
        completed: Boolean = false,
        isCompletedVisible: Boolean = false,
        onSave: (String, String, Boolean) -> Unit,
        onDelete: (() -> Unit)? = null
    ) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_task, null)
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = dialogBuilder.show()

        val taskTitleEditText: EditText = dialogView.findViewById(R.id.taskTitleEditText)
        val taskDescriptionEditText: EditText = dialogView.findViewById(R.id.taskDescriptionEditText)
        val saveTaskButton: Button = dialogView.findViewById(R.id.saveTaskButton)
        val taskCompletedCheckBox: CheckBox = dialogView.findViewById(R.id.taskCompletedCheckBox)
        val deleteTaskButton: Button = dialogView.findViewById(R.id.deleteTaskButton)

        taskTitleEditText.setText(title)
        taskDescriptionEditText.setText(description)
        taskCompletedCheckBox.isChecked = completed
        taskCompletedCheckBox.visibility = if (isCompletedVisible) View.VISIBLE else View.GONE
        deleteTaskButton.visibility = if (onDelete != null) View.VISIBLE else View.GONE

        saveTaskButton.setOnClickListener {
            val updatedTitle = taskTitleEditText.text.toString()
            val updatedDescription = taskDescriptionEditText.text.toString()
            val updatedCompleted = taskCompletedCheckBox.isChecked

            if (updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty()) {
                onSave(updatedTitle, updatedDescription, updatedCompleted)
                alertDialog.dismiss()
            } else {
                if (updatedTitle.isEmpty()) {
                    taskTitleEditText.error = "Title is required"
                }
                if (updatedDescription.isEmpty()) {
                    taskDescriptionEditText.error = "Description is required"
                }
            }
        }

        onDelete?.let {
            deleteTaskButton.setOnClickListener {
                it()
                alertDialog.dismiss()
            }
        }
    }
}
