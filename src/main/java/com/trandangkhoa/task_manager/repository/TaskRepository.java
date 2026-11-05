package com.trandangkhoa.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trandangkhoa.task_manager.entity.Task; // import public class Task in entity package
import com.trandangkhoa.task_manager.entity.Status; // import public class Status in entity package

import java.util.List; // List is an interface in package java.util to save a list of elements

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Spring Data JPA reads method and automatically create query --> Find all tasks which has corresponding projectId
    List<Task> findByProjectId(Long projectId);

    // Find all tasks which has corresponding projectID and status
    List<Task> findByProjectIdAndStatus(Long projectId, Status status);

    //Because Task has *-to-1 relationship to Project, so Spring Data JPA can access project.id through projectID FK
}