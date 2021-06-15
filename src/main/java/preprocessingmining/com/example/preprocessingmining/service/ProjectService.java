package preprocessingmining.com.example.preprocessingmining.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import preprocessingmining.com.example.preprocessingmining.model.Project;
import preprocessingmining.com.example.preprocessingmining.model.ProjectUsers;
import preprocessingmining.com.example.preprocessingmining.model.User;
import preprocessingmining.com.example.preprocessingmining.repository.ProjectRepository;
import preprocessingmining.com.example.preprocessingmining.repository.ProjectUsersRepository;

import java.io.Serializable;
import java.util.UUID;

@Service
public class ProjectService implements Serializable {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectUsersRepository projectUsersRepository;

    public Project getUserByID(@NotNull String userId) {
        return projectRepository.findById(userId)
                .orElse(null);
    }

    public Project save(@NotNull String projectName, @NotNull User user) {
        var project = new Project(projectName);
        project.setId(UUID.randomUUID().toString());
        final var projectSave = projectRepository.save(project);
        final var projectUser = new ProjectUsers(projectSave.getId(),user.getId());
        projectUsersRepository.save(projectUser);
        return projectSave;
    }
}
