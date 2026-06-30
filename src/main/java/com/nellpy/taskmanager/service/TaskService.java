package com.nellpy.taskmanager.service;

import com.nellpy.taskmanager.dto.TaskRequest;
import com.nellpy.taskmanager.dto.TaskResponse;
import com.nellpy.taskmanager.entity.Task;
import com.nellpy.taskmanager.exception.TaskNotFoundException;
import com.nellpy.taskmanager.mapper.TaskMapper;
import com.nellpy.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;


    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }


    public List<TaskResponse> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }


    public List<TaskResponse> getTasksByCompletionStatus(boolean status) {
        return taskRepository.findByCompleted(status)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }


    public TaskResponse findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.toResponse(task);
    }


    public List<TaskResponse> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }


    public TaskResponse createTask(TaskRequest input) {
        Task task = taskMapper.toEntity(input);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }


    public TaskResponse updateTask(Long id, TaskRequest updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskMapper.updateEntityFromRequest(task, updatedTask);
        Task savedTask =  taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }

}
