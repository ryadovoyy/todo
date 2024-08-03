package com.ryadovoy.todo.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TaskCreationRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @NotBlank
    @Size(min = 1, max = 500)
    private String description;
}
