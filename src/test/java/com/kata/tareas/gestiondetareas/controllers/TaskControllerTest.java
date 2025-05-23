package com.kata.tareas.gestiondetareas.controllers;

import com.kata.tareas.gestiondetareas.dto.TaskDTO;
import com.kata.tareas.gestiondetareas.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    private TaskDTO sampleTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTask = new TaskDTO();
        sampleTask.setId(1L);
        sampleTask.setTitle("Test Task");
        sampleTask.setDescription("This is a test task.");
        sampleTask.setUserId(100L);
    }

    @Test
    void testCreateTask() {
        when(taskService.createTask(any(TaskDTO.class))).thenReturn(sampleTask);

        ResponseEntity<TaskDTO> response = taskController.createTask(sampleTask);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleTask, response.getBody());
        verify(taskService, times(1)).createTask(sampleTask);
    }

    @Test
    void testGetAllTasks() {
        List<TaskDTO> tasks = Arrays.asList(sampleTask);
        when(taskService.getTasksByUserId(100L)).thenReturn(tasks);

        ResponseEntity<List<TaskDTO>> response = taskController.getAllTasks(100L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
        verify(taskService, times(1)).getTasksByUserId(100L);
    }

    @Test
    void testUpdateTask() {
        TaskDTO updatedTask = new TaskDTO();
        updatedTask.setId(1L);
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("Updated description");
        updatedTask.setUserId(100L);

        when(taskService.updateTask(eq(1L), any(TaskDTO.class))).thenReturn(updatedTask);

        ResponseEntity<TaskDTO> response = taskController.updateTask(1L, updatedTask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTask, response.getBody());
        verify(taskService, times(1)).updateTask(1L, updatedTask);
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskService).deleteTaskById(1L);

        ResponseEntity<String> response = taskController.deleteTask(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Task deleted successfully!", response.getBody());
        verify(taskService, times(1)).deleteTaskById(1L);
    }
}
