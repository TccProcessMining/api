package preprocessingmining.com.example.preprocessingmining.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import preprocessingmining.com.example.preprocessingmining.model.FileAnalysis;
import preprocessingmining.com.example.preprocessingmining.model.TypeAnalysis;
import preprocessingmining.com.example.preprocessingmining.repository.FileAnalysisRepository;

import java.util.UUID;

@Service
public class FileAnalysisService {
    @Autowired
    private FileAnalysisRepository fileAnalysisRepository;

    public FileAnalysis save(@NotNull FileAnalysis fileAnalysis) {
        fileAnalysis.setId(UUID.randomUUID().toString());
        fileAnalysisRepository.save(fileAnalysis);
        return fileAnalysis;
    }

    public FileAnalysis findByProjectId(@NotNull String projectId, @NotNull TypeAnalysis typeAnalysis){
        return fileAnalysisRepository.findByProjectId(projectId,typeAnalysis);
    }

    public FileAnalysis findByFileId(@NotNull String projectId,@NotNull TypeAnalysis typeAnalysis){
        return fileAnalysisRepository.findByFileId(projectId,typeAnalysis);
    }
}
