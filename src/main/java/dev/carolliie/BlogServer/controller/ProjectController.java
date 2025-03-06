package dev.carolliie.BlogServer.controller;

import dev.carolliie.BlogServer.entity.Post;
import dev.carolliie.BlogServer.entity.PostDTO;
import dev.carolliie.BlogServer.entity.Project;
import dev.carolliie.BlogServer.entity.ProjectDTO;
import dev.carolliie.BlogServer.repository.PostRepository;
import dev.carolliie.BlogServer.repository.ProjectRepository;
import dev.carolliie.BlogServer.service.PostService;
import dev.carolliie.BlogServer.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {


    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        try {
            Project createdProject = projectService.saveProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllProjects());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getProjectBySlug(@PathVariable String slug) {
        try {
            Project project = projectService.getProjectBySlug(slug);

            if (project == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Slug not found");
            }

            return ResponseEntity.ok(project);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable Long projectId) {
        try {
            Project project = projectService.deleteProjectById(projectId);
            return ResponseEntity.ok("Project deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/edit/{projectSlug}")
    public ResponseEntity<?> editProjectBySlug(@PathVariable String projectSlug, @RequestBody ProjectDTO projectDto) {
        try {
            Project project = projectService.editProjectBySlug(projectSlug, projectDto);
            return ResponseEntity.ok("Project edited successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
