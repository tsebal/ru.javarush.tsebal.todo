package ru.javarush.todo.dao;

import ru.javarush.todo.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDao {
    List<Task> findAll(int offset, int limit);

    int findAllCount();

    Optional<Task> findById(int id);

    void saveOrUpdate(Task task);

    void delete(Task task);
}
