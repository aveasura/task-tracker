package org.tracker.cli;

import org.tracker.model.Task;
import org.tracker.model.User;
import org.tracker.model.enums.Priority;
import org.tracker.model.enums.Status;
import org.tracker.service.TaskService;
import org.tracker.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Cli {

    private final TaskService taskService;
    private final UserService userService;
    private final Scanner scanner;

    public Cli(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            int choose = chooseMenu();
            switch (choose) {
                case 0 -> {
                    printMessage("Выход...");
                    return;
                }
                case 1 -> createUser();
                case 2 -> createTask();
                case 3 -> showAllTasks();
                case 4 -> showAllUsers();
                case 5 -> updateTaskStatus();
                case 6 -> assignTask();
                case 7 -> deleteTask();
                case 8 -> deleteUser();
                case 9 -> showTasksForUser();
                default -> printMessage("Неверный выбор");
            }
        }
    }

    private void showTasksForUser() {
        printMessage("Выберите id пользователя у которого надо посмотреть список его задач");
        int userId = validateNumber();
        try {
            List<Task> tasks = userService.getTasksForUser((long) userId);

            if (tasks == null || tasks.isEmpty()) {
                printMessage("Задач у пользователя с id=" + userId + " пока нет");
            } else {
                printMessage("Список задач пользователя с id=" + userId);
                tasks.forEach(System.out::println);
            }
        } catch (NoSuchElementException e) {
            printMessage("Ошибка: " + e.getMessage());
        }
    }

    private void deleteUser() {
        printMessage("введите id пользователя для удаления");
        int userId = validateNumber();

        try {
            userService.deleteById((long) userId);
            printMessage("Пользователь успешно удален");
        } catch (NoSuchElementException e) {
            printMessage("Ошибка: " + e.getMessage());
        }
    }

    private void deleteTask() {
        printMessage("введите id задачи для удаления");
        int taskId = validateNumber();

        try {
            taskService.deleteById((long) taskId);
            printMessage("Задача успешно удалена");
        } catch (NoSuchElementException e) {
            printMessage("Ошибка: " + e.getMessage());
        }
    }

    private void assignTask() {
        printMessage("Выберите id задачи");
        int taskId = validateNumber();

        printMessage("Выберите id пользователя которому нужно назначить эту задачу");
        int userId = validateNumber();

        try {
            taskService.assignTaskToUser((long) taskId, (long) userId);
            printMessage("Задача назначена успешно");
        } catch (NoSuchElementException e) {
            printMessage("Ошибка: " + e.getMessage());
        }
    }

    private int chooseMenu() {
        printMessage("""
                 Что сделать:
                 0. Выйти
                 1. создать пользователя
                 2. создать задачу.
                 3. Список задач
                 4. Список пользователей
                 5. Обновить статус задачи
                 6. Назначить задачу пользователю
                 7. Удалить задачу
                 8. Удалить пользователя
                 9. Показать какие задачи назначены на конкретного пользователя
                """);

        return validateNumber();
    }

    private void updateTaskStatus() {
        printMessage("Укажите id задачи статус которой требуется изменить");
        Long id = (long) validateNumber();
        printMessage("""
                Установите новый status:
                1. NEW
                2. IN_PROGRESS
                3. Done""");
        Status status;
        while (true) {
            int choose = validateNumber();
            switch (choose) {
                case 1 -> status = Status.NEW;
                case 2 -> status = Status.IN_PROGRESS;
                case 3 -> status = Status.DONE;
                default -> {
                    printMessage("Неверный выбор");
                    continue;
                }
            }
            break;
        }

        try {
            taskService.changeStatus(id, status);
            printMessage("Статус задачи успешно обновлен");
        } catch (NoSuchElementException e) {
            printMessage("Ошибка: " + e.getMessage());
        }

    }

    private void showAllTasks() {
        List<Task> tasks = taskService.findAll();
        if (tasks.isEmpty()) {
            printMessage("Задач пока нет");
        } else {
            printMessage("Список задач:");
            tasks.forEach(System.out::println);
        }
    }

    private void showAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            printMessage("Пользователей пока нет");
        } else {
            printMessage("Список пользователей:");
            users.forEach(System.out::println);
        }
    }

    private void createTask() {
        printMessage("Введите title");
        final String title = scanner.nextLine();

        printMessage("Введите description");
        final String description = scanner.nextLine();

        printMessage("""
                Выберите priority:
                1. LOW
                2. MEDIUM
                3. HIGH""");
        Priority priority;
        while (true) {
            int priorityChoose = validateNumber();
            switch (priorityChoose) {
                case 1 -> priority = Priority.LOW;
                case 2 -> priority = Priority.MEDIUM;
                case 3 -> priority = Priority.HIGH;
                default -> {
                    printMessage("Выберите цифру из списка приоритетов");
                    continue;
                }
            }
            break;
        }

        final LocalDateTime dueDate = readDueDate(scanner);
        printMessage("""
                Введите status:
                1. NEW
                2. IN_PROGRESS
                3. Done""");
        Status status;
        while (true) {
            int statusChoose = validateNumber();
            switch (statusChoose) {
                case 1 -> status = Status.NEW;
                case 2 -> status = Status.IN_PROGRESS;
                case 3 -> status = Status.DONE;
                default -> {
                    printMessage("Выберите цифру из списка приоритетов");
                    continue;
                }
            }
            break;
        }

        Task task = new Task(title, description, priority, dueDate, status);
        taskService.save(task);
        printMessage("Задача успешно добавлена");
    }

    private void createUser() {
        printMessage("Введите имя");
        final String name = scanner.nextLine();

        printMessage("Введите email");
        final String email = scanner.nextLine();

        User user = new User(name, email);
        userService.save(user);

        printMessage("Пользователь успешно добавлен");
    }

    private int validateNumber() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Введите цифру");
            }
        }
    }

    private LocalDateTime readDueDate(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            printMessage("Введите dueDate в формате yyyy-MM-dd HH:mm");
            printMessage("например 2025-11-21 12:39");
            String input = scanner.nextLine();

            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                printMessage("Неправильный формат даты! Попробуйте ещё раз.");
            }
        }
    }


    private void printMessage(String s) {
        System.out.println(s);
    }
}
