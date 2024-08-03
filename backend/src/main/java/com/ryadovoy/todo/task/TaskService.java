package com.ryadovoy.todo.task;

import com.ryadovoy.todo.task.dto.TaskCreationRequest;
import com.ryadovoy.todo.task.dto.TaskUpdateRequest;
import com.ryadovoy.todo.task.exception.TaskNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task create(TaskCreationRequest taskCreationRequest) {
        Task task = new Task(taskCreationRequest.getTitle(), taskCreationRequest.getDescription());
        taskRepository.save(task);
        log.info("Task created: {}", task);
        return task;
    }

    public Task update(Long id, TaskUpdateRequest taskUpdateRequest) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskUpdateRequest.getTitle());
                    task.setDescription(taskUpdateRequest.getDescription());
                    task.setCompleted(taskUpdateRequest.isCompleted());

                    taskRepository.save(task);
                    log.info("Task updated: {}", task);
                    return task;
                })
                .orElseThrow(() -> new TaskNotFoundException("No task found with id: " + id));
    }

    public void remove(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("No task found with id: " + id);
        }
        taskRepository.deleteById(id);
        log.info("Task deleted with id: {}", id);
    }
}
