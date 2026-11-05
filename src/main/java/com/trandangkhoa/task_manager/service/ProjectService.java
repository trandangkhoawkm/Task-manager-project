// Service contains the business logic
// Flow: Client --> Controller --> ProjectService --> ProjectRepository --> Database
package com.trandangkhoa.task_manager.service;

import org.springframework.stereotype.Service;

import com.trandangkhoa.task_manager.entity.Project;
import com.trandangkhoa.task_manager.repository.ProjectRepository;
import com.trandangkhoa.task_manager.exception.NotFoundException;
import java.util.List;

@Service // mark this is a service component, Spring will manage and allow to inject into other classes
public class ProjectService {

    private final ProjectRepository repo;

    public ProjectService(ProjectRepository repo) {
        this.repo = repo; // constructor injection
    }

    // create
    public Project create(Project project) {
        return repo.save(project); // save() is a method inherited from JpaRepository
    }

    // find all
    public List<Project> findAll() { // List<Project> is a list contains many projects
        return repo.findAll(); // get all records from Project table
    }

    // find by ID
    public Project findById(Long id) {
        return repo.findById(id) // this method inherits from interface JpaRepository of Spring Data JPA)
        // this findById() method return Optional<Project>, which handles both case: able to find Project and unable to find Project
                .orElseThrow(() -> new NotFoundException("Project " + id + " not found"));
                // if it's unable to find Project, throw an Exception announcing that "Project ... not found"
    }

    // update
    public Project update(Long id, Project updatedProject) {
        Project project = findById(id); // reuse existing method above to find the current project
        // if project exists, then update, otherwise, throw Exception

        // update info
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());

        // save to the dtb
        return repo.save(project);
    }

    // delete
    public void delete(Long id) {
        Project project = findById(id); // find project, if exist --> delete, otherwise --> throw Exception
        repo.delete(project);
    }
}