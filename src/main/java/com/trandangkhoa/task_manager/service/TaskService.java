package com.trandangkhoa.task_manager.service;

import com.trandangkhoa.task_manager.entity.Project;
import com.trandangkhoa.task_manager.entity.Status;
import com.trandangkhoa.task_manager.entity.Task;
import com.trandangkhoa.task_manager.exception.NotFoundException;
import com.trandangkhoa.task_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service // mark as a service component
public class TaskService {
    private final TaskRepository taskRepo;
    private final ProjectService projectService;

    public TaskService(TaskRepository taskRepo, ProjectService projectService) {
        this.taskRepo = taskRepo;
        this.projectService = projectService;
    }

    public Task create(Long projectId, Task task) {
        Project project = projectService.findById(projectId); // 404 if missing
        task.setProject(project);
        return taskRepo.save(task);
    }

    public List<Task> findByProject(Long projectId, Status status) {
        projectService.findById(projectId);
        return status == null
                ? taskRepo.findByProjectId(projectId)
                : taskRepo.findByProjectIdAndStatus(projectId, status);
    }

    public Task findById(Long id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Task " + id + " not found"));
    }

    @Transactional
    public Task update(Long id, Task changes) {
        Task existing = findById(id);   // throw NotFoundException (404) if missing

        existing.setTitle(changes.getTitle());
        existing.setDescription(changes.getDescription());
        existing.setPriority(changes.getPriority());
        existing.setStatus(changes.getStatus());
        existing.setDueDate(changes.getDueDate());

        return taskRepo.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Task existing = findById(id);   // throw NotFoundException (404) if missing
        taskRepo.delete(existing);
    }
}
