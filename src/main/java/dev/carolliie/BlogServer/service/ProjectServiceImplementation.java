package dev.carolliie.BlogServer.service;

import com.github.slugify.Slugify;
import dev.carolliie.BlogServer.entity.Project;
import dev.carolliie.BlogServer.entity.ProjectDTO;
import dev.carolliie.BlogServer.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImplementation implements ProjectService {

    final Slugify slg = Slugify.builder().build();

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveProject(Project project) {
        String result = slg.slugify(project.getName());
        project.setDate(new Date());
        project.setSlug(result);

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectBySlug(String slug) {
        Optional<Project> project = projectRepository.findBySlug(slug);
        if (project.isPresent()) {
            return project.get();
        } else {
            throw new EntityNotFoundException("Project not found");
        }
    }

    public Project deleteProjectById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            projectRepository.delete(project.get());
            return project.get();
        } else {
            throw new EntityNotFoundException("Project not found or deleted.");
        }
    }

    public Project editProjectBySlug(String projectSlug, ProjectDTO projectDto) {
        Optional<Project> optionalProject = projectRepository.findBySlug(projectSlug);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            if (projectDto.getName() != null) {
                project.setName(projectDto.getName());
                String newSlug = slg.slugify(projectDto.getName());
                project.setSlug(newSlug);
            }
            if (projectDto.getContent() != null) {
                project.setContent(projectDto.getContent());
            }
            if (projectDto.getImg() != null) {
                project.setImg(projectDto.getImg());
            }
            if (projectDto.getProjectColor() != null) {
                project.setProjectColor(projectDto.getProjectColor());
            }
            if (projectDto.getTagColor() != null) {
                project.setTagColor(projectDto.getTagColor());
            }
            if (projectDto.getTagTextColor() != null) {
                project.setTagTextColor(projectDto.getTagTextColor());
            }
            if (projectDto.getDate() != null) {
                project.setDate(projectDto.getDate());
            }
            if (projectDto.getTags() != null) {
                project.setTags(projectDto.getTags());
            }

            projectRepository.save(project);
            return project;
        } else {
            throw new EntityNotFoundException("Project not found or deleted.");
        }
    }
}
