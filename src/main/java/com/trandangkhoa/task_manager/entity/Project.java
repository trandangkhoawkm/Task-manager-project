package com.trandangkhoa.task_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // mark as the class is a JPA Entity, which represents for a table in dtb
@Table(name = "projects") // Name the dtb table is projects
public class Project {
    @Id // mark as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generate id automatically, GenerationType.IDENTITY raises the ID by 1 automatically
    private Long id;

    @NotBlank(message = "Project name cannot be blank") // Not accept null and space only values in name column at Application layer
    @Size(max = 100)
    @Column(nullable = false) // not allow null values at dtb layer
    private String name;

    private String description;

    // Default constructor
    public Project() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}