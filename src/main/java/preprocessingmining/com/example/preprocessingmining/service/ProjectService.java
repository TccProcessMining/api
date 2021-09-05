package preprocessingmining.com.example.preprocessingmining.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import preprocessingmining.com.example.preprocessingmining.model.Field;
import preprocessingmining.com.example.preprocessingmining.model.Project;
import preprocessingmining.com.example.preprocessingmining.model.ProjectUsers;
import preprocessingmining.com.example.preprocessingmining.model.User;
import preprocessingmining.com.example.preprocessingmining.repository.FileFieldsTypeRepository;
import preprocessingmining.com.example.preprocessingmining.repository.ProjectRepository;
import preprocessingmining.com.example.preprocessingmining.repository.ProjectUsersRepository;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService implements Serializable {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectUsersRepository projectUsersRepository;
    @Autowired
    private FileFieldsTypeRepository fileFieldsTypeRepository;

    public Project findProjectByID(@NotNull String userId) {
        return projectRepository.findById(userId)
                .orElse(null);
    }

    public Project save(@NotNull String projectName, @NotNull User user) {
        var project = new Project(projectName);
        project.setId(UUID.randomUUID().toString());
        final var projectSave = projectRepository.save(project);
        final var projectUser = new ProjectUsers(projectSave.getId(), user.getId());
        projectUsersRepository.save(projectUser);
        return projectSave;
    }

    public List<Field> saveFilds(@NotNull List<Field> fields) {
        return  fields.stream().map(field -> {
            field.setId(UUID.randomUUID().toString());
            return fileFieldsTypeRepository.save(field);
        }).collect(Collectors.toList());
    }

    public List<Field> getFilds(@NotNull String projectID){
        return fileFieldsTypeRepository.findByProjectID(projectID);
    }
    public List<Field>  getFilds(@NotNull String projectID, @NotNull String fileId){
        return fileFieldsTypeRepository.findByProjectIDAndFileID(projectID,fileId);
    }

}
