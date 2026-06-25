package com.nellpy.taskmanager.service;

import com.nellpy.taskmanager.entity.Task;
import com.nellpy.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public boolean deleteTask(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return true;
                }).orElse(false);
    }


    public List<Task> findAll() {
        return taskRepository.findAll();
    }


    public List<Task> getTasksByCompletionStatus(boolean status) {
        return taskRepository.findByCompleted(status);
    }


    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }


    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }


    public Task createTask(Task input) {
        return taskRepository.save(input);
    }


    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.getCompleted());
                    return taskRepository.save(task);
                });
    }
}
