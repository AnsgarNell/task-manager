package com.nellpy.taskmanager.service;

import com.nellpy.taskmanager.entity.Task;
import com.nellpy.taskmanager.exception.TaskNotFoundException;
import com.nellpy.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }


    public List<Task> findAll() {
        return taskRepository.findAll();
    }


    public List<Task> getTasksByCompletionStatus(boolean status) {
        return taskRepository.findByCompleted(status);
    }


    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }


    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }


    public Task createTask(Task input) {
        return taskRepository.save(input);
    }


    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.getCompleted());
        return taskRepository.save(task);
    }
}
