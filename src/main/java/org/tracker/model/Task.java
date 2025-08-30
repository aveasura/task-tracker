package org.tracker.model;

import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;

import java.time.LocalDateTime;

public class Task {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private User assignee;
    private LocalDateTime dueDate;

    public Task(String description, LocalDateTime dueDate, Priority priority, Status status, String title) {
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.title = title;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
