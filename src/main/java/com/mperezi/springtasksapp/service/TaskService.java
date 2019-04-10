package com.mperezi.springtasksapp.service;

import com.mperezi.springtasksapp.config.exception.EntityNotFoundException;
import com.mperezi.springtasksapp.domain.Task;
import com.mperezi.springtasksapp.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task create(Task task) {
        task.setId(null);
        Task newTask = taskRepository.save(task);
        log.debug("Task {} created (title=\"{}\")", newTask.getId(), newTask.getTitle());
        return newTask;
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> taskNotFoundWithId(id));
    }

    public Task finish(Long id) {
        Task task = getTask(id);
        task.setFinished(true);
        log.debug("Task {} finished", task.getId());
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            log.debug("Task {} deleted", id);
        } else {
            throw taskNotFoundWithId(id);
        }
    }

    public Task update(Task task) {
        Task oldTask = getTask(task.getId());

        if (task.getTitle() != null) {
            oldTask.setTitle(task.getTitle());
        }

        if (task.getNotes() != null) {
            oldTask.setNotes(task.getNotes());
        }

        Task updatedTask = taskRepository.save(oldTask);
        log.debug("Task {} updated", updatedTask.getId());
        return updatedTask;
    }

    private EntityNotFoundException taskNotFoundWithId(Long id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id.toString());
        return new EntityNotFoundException(Task.class, parameters);
    }

    public void mock() {
        Stream.of(
                Task.builder().title("Empty the trash bin").build(),
                Task.builder().title("Do the dishes").build(),
                Task.builder().title("Fix the alarm clock").finished(true).build()
        ) .forEach(taskRepository::save);
    }
}
