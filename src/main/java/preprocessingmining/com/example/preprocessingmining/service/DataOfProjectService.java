package preprocessingmining.com.example.preprocessingmining.service;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import preprocessingmining.com.example.preprocessingmining.model.DataOfProject;
import preprocessingmining.com.example.preprocessingmining.repository.DataOfProjectRepository;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DataOfProjectService implements Serializable {
    @Autowired
    private DataOfProjectRepository dataOfProjectRepository;
    @Autowired
    private  ProjectService projectService;

    @SneakyThrows
    public DataOfProject store(@NotNull String projectId, @NotNull MultipartFile file) throws IOException {
        var fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        final var project = projectService.findProjectByID(projectId);
        if(project == null || !Objects.equals(file.getContentType(), "text/csv")) {
            throw new Exception();
        }
        var save = new DataOfProject(UUID.randomUUID().toString(),projectId,
                            fileName, file.getContentType(), ByteBuffer.wrap(file.getBytes()));
        return dataOfProjectRepository.save(save);
    }

    public List<DataOfProject> listByProjectId(@NotNull String projectId){
        return dataOfProjectRepository.findByProjectId(projectId);
    }

}