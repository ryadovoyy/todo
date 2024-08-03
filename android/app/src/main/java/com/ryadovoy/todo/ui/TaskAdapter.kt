package com.ryadovoy.todo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ryadovoy.todo.R
import com.ryadovoy.todo.data.Task
import com.ryadovoy.todo.data.TaskUpdateRequest

class TaskAdapter(
    private val tasks: List<Task>,
    private val viewModel: TaskViewModel,
    private val onTaskClicked: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
        holder.itemView.setOnClickListener {
            onTaskClicked(task)
        }
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val completedCheckBox: CheckBox = itemView.findViewById(R.id.completedCheckBox)

        fun bind(task: Task) {
            titleTextView.text = task.title
            completedCheckBox.isChecked = task.completed

            completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateTask(task.id, TaskUpdateRequest(
                    title = task.title,
                    description = task.description,
                    completed = isChecked
                ))
            }
        }
    }
}
