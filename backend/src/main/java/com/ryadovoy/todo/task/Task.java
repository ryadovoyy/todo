package com.ryadovoy.todo.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_seq"
    )
    @SequenceGenerator(
            name = "task_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean completed = false;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
