package com.kata.tareas.gestiondetareas.service;

import com.kata.tareas.gestiondetareas.dto.TaskDTO;
import com.kata.tareas.gestiondetareas.exception.TaskNotFoundException;
import com.kata.tareas.gestiondetareas.model.Task;
import com.kata.tareas.gestiondetareas.model.User;
import com.kata.tareas.gestiondetareas.repository.TaskRepository;
import com.kata.tareas.gestiondetareas.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private Task task;
    private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        task = new Task(1L, "Tarea de prueba", "Descripción", false, user);
        taskDTO = new TaskDTO(1L, "Tarea de prueba", "Descripción", false, 1L);
    }

    @Test
    void testCreateTask() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.createTask(taskDTO);

        assertEquals(taskDTO.getTitle(), result.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testGetTasksByUserId() {
        when(taskRepository.findByUserId(1L)).thenReturn(Arrays.asList(task));

        List<TaskDTO> tasks = taskService.getTasksByUserId(1L);

        assertEquals(1, tasks.size());
        assertEquals("Tarea de prueba", tasks.get(0).getTitle());
        verify(taskRepository).findByUserId(1L);
    }

    @Test
    void testUpdateTask_Success() {
        TaskDTO updatedDTO = new TaskDTO(1L, "Actualizada", "Desc actualizada", true, 1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.updateTask(1L, updatedDTO);

        assertEquals("Actualizada", result.getTitle());
        assertTrue(result.isCompleted());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        TaskDTO dto = new TaskDTO(999L, "Test", "Desc", false, 1L);

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(999L, dto));
    }

    @Test
    void testDeleteTask_Success() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTaskById(1L);

        verify(taskRepository).deleteById(1L);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.existsById(999L)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTaskById(999L));
    }

    @Test
    void testCreateTask_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> taskService.createTask(taskDTO));
        assertEquals("Usuario no encontrado con ID: 1", exception.getMessage());
    }
}
