package org.tracker.cli;

import org.tracker.model.Task;
import org.tracker.model.User;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;
import org.tracker.repository.InMemoryTaskRepositoryImpl;
import org.tracker.repository.InMemoryUserRepositoryImpl;
import org.tracker.repository.TaskRepository;
import org.tracker.repository.UserRepository;
import org.tracker.service.TaskService;
import org.tracker.service.TaskServiceImpl;
import org.tracker.service.UserService;
import org.tracker.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        TaskRepository taskRepository = new InMemoryTaskRepositoryImpl();
        UserRepository userRepository = new InMemoryUserRepositoryImpl();

        TaskService taskService = new TaskServiceImpl(taskRepository, userRepository);
        UserService userService = new UserServiceImpl(userRepository, taskRepository);

        Scanner scanner = new Scanner(System.in);

        Cli cli = new Cli(taskService, userService, scanner);
        new Main().fastCreate(userService, taskService);

        cli.start();
    }

    private void fastCreate(UserService userService, TaskService taskService) {
        // юзеры
        userService.save(new User("Alice", "alice@mail.com"));
        userService.save(new User("Bob", "bob@mail.com"));
        userService.save(new User("Charlie", "charlie@mail.com"));

        // таски
        taskService.save(new Task(
                "Fix bug #123",
                "Critical bug in login module",
                Priority.HIGH,
                LocalDateTime.now().plusDays(1),
                Status.NEW)
        );

        taskService.save(new Task(
                "Write docs",
                "Update README and API docs",
                Priority.LOW,
                LocalDateTime.now().plusDays(5),
                Status.NEW)
        );

        taskService.save(new Task(
                "Refactor service layer",
                "Cleanup TaskServiceImpl",
                Priority.MEDIUM,
                LocalDateTime.now().plusDays(3),
                Status.IN_PROGRESS)
        );

        // Назначаем таски юзерам
        taskService.assignTaskToUser(1L, 1L); // таска 1 → Alice
        taskService.assignTaskToUser(3L, 2L); // таска 3 → Bob
    }
}