package com.example.tasks.service;

import com.example.tasks.model.Task;
import com.example.tasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public List<Task> getAll() {
        return repo.findAll();
    }

    public Task getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task create(Task task) {
       return repo.save(task);
    }

    public Task update(Long id, Task task) {
        return repo.findById(id).map(t -> {
            t.setTitle(task.getTitle());
            t.setCompleted(task.isCompleted());
            return repo.save(t);
        }).orElseThrow();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
