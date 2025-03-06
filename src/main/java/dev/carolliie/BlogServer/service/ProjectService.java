package dev.carolliie.BlogServer.service;

import dev.carolliie.BlogServer.entity.Project;
import dev.carolliie.BlogServer.entity.ProjectDTO;

import java.util.List;

public interface ProjectService {

    Project saveProject(Project project);

    List<Project> getAllProjects();

    Project deleteProjectById(Long id);

    Project getProjectBySlug(String slug);

    Project editProjectBySlug(String projectSlug, ProjectDTO projectDto);
}
