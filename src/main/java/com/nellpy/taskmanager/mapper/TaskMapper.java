package com.nellpy.taskmanager.mapper;

import com.nellpy.taskmanager.dto.TaskRequest;
import com.nellpy.taskmanager.dto.TaskResponse;
import com.nellpy.taskmanager.entity.Task;
import org.springframework.stereotype.Component;


@Component
public class TaskMapper {

    public Task toEntity(TaskRequest taskRequest) {
        return Task.builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .completed(taskRequest.completed() != null ? taskRequest.completed() : false)
                .build();
    }


    public TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .createdAt(task.getCreatedAt())
                .build();
    }


    public void updateEntityFromRequest(Task task, TaskRequest updatedTask) {
        task.setTitle(updatedTask.title());
        task.setDescription(updatedTask.description());
        task.setCompleted(updatedTask.completed());
    }

}
