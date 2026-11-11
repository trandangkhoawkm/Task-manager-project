package com.trandangkhoa.task_manager.service;

import com.trandangkhoa.task_manager.entity.Project;
import com.trandangkhoa.task_manager.exception.NotFoundException;
import com.trandangkhoa.task_manager.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock ProjectRepository repo;
    @InjectMocks ProjectService service;

    @Test
    void findById_returnsProject_whenExits() {
        Project p = new Project();
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        assertSame(p, service.findById(1L));
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void create_savesAndReturnsProject() {
        // Arrange
        Project p = new Project();
        when(repo.save(p)).thenReturn(p);

        // Act
        Project result = service.create(p);

        // Assert
        assertSame(p, result);
        verify(repo).save(p);
    }

    @Test
    void delete_callsRepositoryDelete() {
        // Arrange
        Project p = new Project();
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        // Act
        service.delete(1L);

        // Assert
        verify(repo).delete(p);
    }
}