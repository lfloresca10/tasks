package com.example.tasks.service;

import com.example.tasks.model.Task;
import com.example.tasks.repository.TaskRepository;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository repo;

    @InjectMocks
    private TaskService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        Task task2 = new Task();
        task2.setTitle("Task 2");

        when(repo.findAll()).thenReturn(List.of(task1, task2));
        List<Task> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testCreate() {
        Task task = new Task();
        task.setTitle("New Task");
        when(repo.save(any(Task.class))).thenReturn(task);

        Task saved = service.create(task);
        assertEquals("New Task", saved.getTitle());
        verify(repo, times(1)).save(task);
    }

    @Test
    void testUpdate() {
        Task existing = new Task();
        existing.setId(1L);
        existing.setTitle("Old");
        existing.setCompleted(false);

        Task updated = new Task();
        updated.setTitle("New");
        updated.setCompleted(true);

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Task.class))).thenReturn(existing);

        Task result = service.update(1L, updated);

        assertEquals("New", result.getTitle());
        assertTrue(result.isCompleted());
        verify(repo).save(existing);
    }

    @Test
    void testDelete() {
        service.delete(1L);
        verify(repo, times(1)).deleteById(1L);
    }
}
