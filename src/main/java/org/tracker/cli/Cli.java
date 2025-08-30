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
        int userId = validateNumber(scanner);
        List<Task> tasks = userService.getTasksForUser((long) userId);
        tasks.forEach(System.out::println);
    }

    private void deleteUser() {
        printMessage("введите id пользователя для удаления");
        int userId = validateNumber(scanner);
        userService.deleteById((long) userId);
    }

    private void deleteTask() {
        printMessage("введите id задачи для удаления");
        int taskId = validateNumber(scanner);
        taskService.deleteById((long) taskId);
    }

    private void assignTask() {
        printMessage("Выберите id задачи");
        int taskId = validateNumber(scanner);

        printMessage("Выберите id пользователя которому нужно назначить эту задачу");
        int userId = validateNumber(scanner);

        taskService.assignTaskToUser((long) taskId, (long) userId);
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

        return validateNumber(scanner);
    }

    private void updateTaskStatus() {
        printMessage("Укажите id задачи статус которой требуется изменить");
        Long id = (long) validateNumber(scanner);
        printMessage("""
                Установите новый status:
                1. NEW
                2. IN_PROGRESS
                3. Done""");
        Status status = null;
        while (true) {
            int choose = validateNumber(scanner);
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
        taskService.changeStatus(id, status);

    }

    private void showAllTasks() {
        List<Task> tasks = taskService.findAll();
        tasks.forEach(System.out::println);
    }

    private void showAllUsers() {
        List<User> users = userService.findAll();
        users.forEach(System.out::println);
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
        Priority priority = null;
        while (true) {
            int priorityChoose = validateNumber(scanner);
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
        Status status = null;
        while (true) {
            int statusChoose = validateNumber(scanner);
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

        System.out.println("После добавления:");
        showAllTasks();
    }

    private void createUser() {
        printMessage("Введите имя");
        final String name = scanner.nextLine();

        printMessage("Введите email");
        final String email = scanner.nextLine();

        User user = new User(name, email);
        userService.save(user);

        System.out.println("После добавления:");
        showAllUsers();
    }

    private int validateNumber(Scanner scanner) {
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
