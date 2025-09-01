package org.tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
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
    public void run(String... args) throws Exception {
        userService.save(new User("Alice", "alice@mail.com"));
        userService.save(new User("Bob", "bob@mail.com"));
        userService.save(new User("Charlie", "charlie@mail.com"));

        taskService.save(new Task("Task1", "desc1", Priority.HIGH, LocalDateTime.now(), Status.NEW));
        taskService.save(new Task("Task2", "desc2", Priority.LOW, LocalDateTime.now(), Status.NEW));
        taskService.save(new Task("Task3", "desc3", Priority.MEDIUM, LocalDateTime.now(), Status.IN_PROGRESS));

        taskService.assignTaskToUser(1L, 1L); // таска 1 → Alice
        taskService.assignTaskToUser(3L, 2L); // таска 3 → Bob
    }
}
