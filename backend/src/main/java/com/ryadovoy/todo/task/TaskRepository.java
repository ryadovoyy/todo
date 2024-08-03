package com.ryadovoy.todo.task;

import org.springframework.data.repository.ListCrudRepository;

public interface TaskRepository extends ListCrudRepository<Task, Long> {
}
