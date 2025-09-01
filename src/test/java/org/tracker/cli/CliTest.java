package org.tracker.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.tracker.service.TaskService;
import org.tracker.service.UserService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

class CliTest {

    private TaskService mockTaskService;
    private UserService mockUserService;
    private ByteArrayOutputStream outContent;
    private Cli cli;

    @BeforeEach
    void setUp() {
        mockTaskService = Mockito.mock(TaskService.class);
        mockUserService = Mockito.mock(UserService.class);
        outContent = new ByteArrayOutputStream();
    }

    @Test
    void deleteTask_Success() {
        cli = new Cli(mockTaskService, mockUserService, new Scanner("1\n"));
        System.setOut(new PrintStream(outContent));

        cli.deleteTask();

        Mockito.verify(mockTaskService).deleteById(1L);

        Assertions.assertTrue(outContent.toString().contains("Задача успешно удалена"));

        System.setOut(System.out);
    }
}
