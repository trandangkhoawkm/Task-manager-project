package com.trandangkhoa.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trandangkhoa.task_manager.entity.Project; //import public class Project in entity package

//
public interface ProjectRepository extends JpaRepository<Project, Long>{ // this interface makes ProjectRepository inherits interface JpaRepository --> ProjectRepository can use methods provided by JpaRepository
    //<Project, Long>: Entity which is managed by Repository, Long is the data type of PK (id) in Project
    //We need to pass parameters in this case because Spring Data JPA need to know Repository manages which entity and the data type of PK
    // Because this interface inherits from JpaRepository, therefore it contains methods such as findAll(), findById(), save(), deleteById(),...
}