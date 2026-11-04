package com.trandangkhoa.task_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity // mark the class is a JPA Entity
@Table(name = "tasks") // name the table
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Task title cannot be blank")
    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING) // set the specification of saving Enum into dtb
    private Priority priority; // only allow to enter strings which are defined in public enum Priority{...}

    @Enumerated(EnumType.STRING) // Set the specification of saving Enum into dtb
    private Status status; // only allow to enter strings which are defined in public enum Status{...}

    private LocalDate dueDate; // Local Date is the type of data the save date/month/year without hour and minute

    @ManyToOne(fetch = FetchType.LAZY) // Create a relationship 1-to-* between project table and task table
    @JoinColumn(name = "project_id") // Create a foreign key column named project_id in tasks table
    private Project project;

    // Default constructor
    public Task() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Project getProject() {
        return project;
    }

    void setProject(Project project) {
        this.project = project;
    }
}
