package com.ryadovoy.todo.task;

import com.ryadovoy.todo.task.dto.TaskCreationRequest;
import com.ryadovoy.todo.task.dto.TaskUpdateRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@Valid @RequestBody TaskCreationRequest taskCreationRequest) {
        return taskService.create(taskCreationRequest);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest taskUpdateRequest) {
        return taskService.update(id, taskUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        taskService.remove(id);
    }
}
