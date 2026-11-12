package com.trandangkhoa.task_manager.controller;

import com.trandangkhoa.task_manager.entity.Status;
import com.trandangkhoa.task_manager.entity.Task;
import com.trandangkhoa.task_manager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping("/projects/{projectId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@PathVariable Long projectId, @Valid @RequestBody Task task) {
        return service.create(projectId, task);
    }

    @GetMapping("/projects/{projectId}/tasks")
    public List<Task> list(@PathVariable Long projectId,
                           @RequestParam(required = false) Status status){
        return service.findByProject(projectId, status);
    }

    @GetMapping("/tasks/{id}")
    public Task one(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/tasks/{id}")
    public Task update(@PathVariable Long id, @Valid @RequestBody Task task) {
        return service.update(id, task);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/tasks/{id}/advance")
    public Task advance(@PathVariable Long id) {
        return service.advanceStatus(id);
    }
}