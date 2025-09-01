//package org.tracker.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.tracker.model.Task;
//import org.tracker.model.User;
//import org.tracker.model.enums.Priority;
//import org.tracker.model.enums.Status;
//import org.tracker.repository.TaskRepository;
//import org.tracker.repository.UserRepository;
//
//import java.time.LocalDateTime;
//import java.util.NoSuchElementException;
//
//public class TaskServiceImplTest {
//
//    private TaskRepository mockTaskRepo;
//    private UserRepository mockUserRepo;
//    private TaskService service;
//
//    @BeforeEach
//    void setUp() {
//        mockTaskRepo = Mockito.mock(TaskRepository.class);
//        mockUserRepo = Mockito.mock(UserRepository.class);
//        service = new TaskServiceImpl(mockTaskRepo, mockUserRepo);
//    }
//
//    @Test
//    void assignTaskToUserTest_Success() {
//        Task task = new Task("Task1",
//                "this is task1",
//                Priority.HIGH,
//                LocalDateTime.of(2025, 11, 25, 22, 58),
//                Status.NEW);
//        task.setId(1L);
//
//        User user = new User("Name", "ya.ru");
//        user.setId(2L);
//
////        Mockito.when(mockTaskRepo.findById(1L)).thenReturn(task); // проверка Task task = taskRepository.findById(taskId);
////        Mockito.when(mockUserRepo.findById(2L)).thenReturn(user); // проверка User user = userRepository.findById(userId);
//
//        service.assignTaskToUser(1L, 2L);
//
//        Assertions.assertEquals(user, task.getAssignee());   // проверка task.setAssignee(user);
////        Mockito.verify(mockTaskRepo).update(task);           // проверка taskRepository.update(task);
//    }
//
//    @Test
//    void assignTaskToUser_shouldThrowException_whenTaskNotFound() {
//        User user = new User("Name", "ya.ru");
//        user.setId(2L);
//
//        Mockito.when(mockTaskRepo.findById(1L)).thenReturn(null);
////        Mockito.when(mockUserRepo.findById(2L)).thenReturn(user);
//
//        Assertions.assertThrows(NoSuchElementException.class, () -> service.assignTaskToUser(1L, 2L));
//    }
//
//    @Test
//    void assignTaskToUser_shouldThrowException_whenUserNotFound() {
//        Task task = new Task("Task1",
//                "this is task1",
//                Priority.HIGH,
//                LocalDateTime.now(),
//                Status.NEW);
//        task.setId(1L);
//
////        Mockito.when(mockTaskRepo.findById(1L)).thenReturn(task);
//        Mockito.when(mockUserRepo.findById(2L)).thenReturn(null);
//
//        Assertions.assertThrows(NoSuchElementException.class, () -> service.assignTaskToUser(1L, 2L));
//    }
//
//    @Test
//    void changeStatusTest_shouldChangeStatusSuccessfully() {
//        Task task = new Task("Task1",
//                "this is task1",
//                Priority.HIGH,
//                LocalDateTime.now(),
//                Status.NEW);
//        task.setId(1L);
//
////        Mockito.when(mockTaskRepo.findById(1L)).thenReturn(task);
//
//        service.changeStatus(1L, Status.DONE);
//
//        Assertions.assertEquals(Status.DONE, task.getStatus());
//
////        Mockito.verify(mockTaskRepo).update(task);
//    }
//
//    @Test
//    void changeStatusTest_shouldThrowException_whenTaskNotFound() {
//        Mockito.when(mockTaskRepo.findById(1L)).thenReturn(null);
//        Assertions.assertThrows(NoSuchElementException.class, () -> service.changeStatus(1L, Status.DONE));
//    }
//
//    @Test
//    void deleteById_Success() {
//        Task task = new Task("Task1",
//                "this is task1",
//                Priority.HIGH,
//                LocalDateTime.now(),
//                Status.NEW);
//        task.setId(1L);
//
////        Mockito.when(mockTaskRepo.findById(1L)).thenReturn(task);
//
//        service.deleteById(1L);
//
//        Mockito.verify(mockTaskRepo).deleteById(1L);
//    }
//
//    @Test
//    void deleteById_shouldThrowException_whenTaskNotFound() {
//        Mockito.when(mockTaskRepo.findById(1L)).thenReturn(null);
//        Assertions.assertThrows(NoSuchElementException.class, () -> service.deleteById(1L));
//    }
//}
