package ru.javarush.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.todo.dao.TaskDao;
import ru.javarush.todo.entity.Status;
import ru.javarush.todo.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public List<Task> findAll(int offset, int limit) {
        return taskDao.findAll(offset, limit);
    }

    public int findAllCount() {
        return taskDao.findAllCount();
    }

    @Transactional
    public Task edit(int id, String description, Status status) {
        if (taskDao.findById(id).isEmpty()) {
            logger.error("The task ID: " + id + " you requested does not found.");
            throw new RuntimeException("The task is not found.");
        }

        Task task = taskDao.findById(id).get();
        task.setDescription(description);
        task.setStatus(status);
        taskDao.saveOrUpdate(task);
        logger.info("The task ID: " + id + " successfully edited.");
        return task;
    }

    public Task create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDao.saveOrUpdate(task);
        logger.info("The task is successfully created.");
        return task;
    }

    @Transactional
    public void delete(int id) {
        Optional<Task> task = taskDao.findById(id);
        if (task.isEmpty()) {
            logger.error("The task ID: " + id + " you requested does not found.");
            throw new RuntimeException("The task is not found.");
        }

        taskDao.delete(task.get());
        logger.info("The task ID: " + id + " successfully deleted.");
    }
}
