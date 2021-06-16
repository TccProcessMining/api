package preprocessingmining.com.example.preprocessingmining.service;

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
import java.util.Objects;
import java.util.UUID;

@Service
public class DataOfProjectService implements Serializable {
    @Autowired
    private DataOfProjectRepository dataOfProjectRepository;

    public DataOfProject store(@NotNull MultipartFile file) throws IOException {
        var fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        var save = new DataOfProject(UUID.randomUUID(),fileName, file.getContentType(), ByteBuffer.wrap(file.getBytes()));
        try {
            return dataOfProjectRepository.save(save);
        }catch (Exception e){
            e.printStackTrace();
            return save;
        }

    }

}