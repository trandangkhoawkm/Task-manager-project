package com.trandangkhoa.task_manager.service;

import com.trandangkhoa.task_manager.entity.Priority;
import com.trandangkhoa.task_manager.entity.Project;
import com.trandangkhoa.task_manager.entity.Status;
import com.trandangkhoa.task_manager.entity.Task;
import com.trandangkhoa.task_manager.exception.NotFoundException;
import com.trandangkhoa.task_manager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock TaskRepository taskRepo;
    @Mock ProjectService projectService;
    @InjectMocks TaskService service;

    @Test
    void create_attachesProjectAndSaves() {
        Project p = new Project();
        Task t = new Task();
        when(projectService.findById(1L)).thenReturn(p);
        when(taskRepo.save(t)).thenReturn(t);

        Task saved = service.create(1L, t);

        assertSame(p, saved.getProject());
        verify(taskRepo).save(t);
    }

    @Test
    void create_throwsNotFound_whenProjectMissing() {
        when(projectService.findById(99L))
                .thenThrow(new NotFoundException("Project 99 not found"));

        assertThrows(NotFoundException.class, () -> service.create(99L, new Task()));
        verify(taskRepo, never()).save(any());
    }

    @Test
    void findByProject_withoutStatus_usesUnfilteredQuery() {
        // Arrange
        Task t = new Task();
        when(taskRepo.findByProjectId(1L)).thenReturn(List.of(t));

        // Act
        List<Task> result = service.findByProject(1L, null);

        // Assert
        assertEquals(1, result.size());
        assertSame(t, result.get(0));
        verify(projectService).findById(1L);          // project existence is checked
        verify(taskRepo).findByProjectId(1L);
        verify(taskRepo, never()).findByProjectIdAndStatus(any(), any());
    }

    @Test
    void findByProject_withStatus_usesFilteredQuery() {
        // Arrange
        Task t = new Task();
        when(taskRepo.findByProjectIdAndStatus(1L, Status.TODO)).thenReturn(List.of(t));

        // Act
        List<Task> result = service.findByProject(1L, Status.TODO);

        // Assert
        assertEquals(1, result.size());
        assertSame(t, result.get(0));
        verify(projectService).findById(1L);
        verify(taskRepo).findByProjectIdAndStatus(1L, Status.TODO);
        verify(taskRepo, never()).findByProjectId(any());
    }

    @Test
    void update_changesFields() {
        // Arrange
        Project p = new Project();
        Task existing = new Task();
        existing.setTitle("Old title");
        existing.setDescription("Old description");
        existing.setPriority(Priority.LOW);
        existing.setStatus(Status.TODO);
        existing.setProject(p);

        Task changes = new Task();
        changes.setTitle("New title");
        changes.setDescription("New description");
        changes.setPriority(Priority.HIGH);
        changes.setStatus(Status.DOING);
        changes.setDueDate(LocalDate.of(2026, 11, 20));

        when(taskRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepo.save(existing)).thenReturn(existing);

        // Act
        Task result = service.update(1L, changes);

        // Assert
        assertEquals("New title", result.getTitle());
        assertEquals("New description", result.getDescription());
        assertEquals(Priority.HIGH, result.getPriority());
        assertEquals(Status.DOING, result.getStatus());
        assertEquals(LocalDate.of(2026, 11, 20), result.getDueDate());
        assertSame(p, result.getProject());            // project must not change
        verify(taskRepo).save(existing);
    }
}