package com.trandangkhoa.task_manager.controller;

import com.trandangkhoa.task_manager.entity.Project;   // đổi theo package thật của bạn
import com.trandangkhoa.task_manager.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project create(@Valid @RequestBody Project project) {
        return service.create(project);
    }

    @GetMapping
    public List<Project> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Project one(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable Long id, @Valid @RequestBody Project project) {
        return service.update(id, project);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}