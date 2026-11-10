package com.trandangkhoa.task_manager.service; // declare this class in the service package

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
    // this dependency used to perform operations on Task in dtb (e.g: update task, find task by id, delete task,...)
    private final TaskRepository taskRepo;
    // this dependency used to call processing function from projectService
    private final ProjectService projectService;

    public TaskService(TaskRepository taskRepo, ProjectService projectService) {
        // Constructor injection
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

    public Task update(Long id, Task changes) {
        Task task = findById(id);              // throws 404 if missing
        task.setTitle(changes.getTitle());
        task.setDescription(changes.getDescription());
        task.setPriority(changes.getPriority());
        task.setStatus(changes.getStatus());
        task.setDueDate(changes.getDueDate());
        return taskRepo.save(task);
    }

    public void delete(Long id) {
        taskRepo.delete(findById(id));
    }
}
