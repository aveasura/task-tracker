package org.tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tracker.model.Task;
import org.tracker.model.User;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;
import org.tracker.service.TaskService;
import org.tracker.service.UserService;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final TaskService taskService;

    public DataInitializer(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        User alice = new User("Alice", "alice@mail.com");
        User bob = new User("Bob", "bob@mail.com");
        User charlie = new User("Charlie", "charlie@mail.com");

        userService.save(alice);
        userService.save(bob);
        userService.save(charlie);

        Task task1 = new Task("Task1", "desc1", Priority.HIGH, LocalDateTime.now(), Status.NEW);
        Task task2 = new Task("Task2", "desc2", Priority.LOW, LocalDateTime.now(), Status.NEW);
        Task task3 = new Task("Task3", "desc3", Priority.MEDIUM, LocalDateTime.now(), Status.IN_PROGRESS);

        taskService.save(task1);
        taskService.save(task2);
        taskService.save(task3);

        taskService.assignTaskToUser(task1.getId(), alice.getId());
        taskService.assignTaskToUser(task3.getId(), bob.getId());
    }
}
