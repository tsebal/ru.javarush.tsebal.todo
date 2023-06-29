package ru.javarush.todo.dto;

import ru.javarush.todo.entity.Status;

public class TaskDto {
    private String description;
    private Status status;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
