package preprocessingmining.com.example.preprocessingmining.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import preprocessingmining.com.example.preprocessingmining.model.FileAnalysis;
import preprocessingmining.com.example.preprocessingmining.model.TypeAnalysis;

@Repository
public interface FileAnalysisRepository extends CrudRepository<FileAnalysis,String> {

    @Query(value = "SELECT * FROM public.file_analysis WHERE project_id = ? and type = ?;", nativeQuery = true)
    FileAnalysis findByProjectId(String project_id, TypeAnalysis type);

    @Query(value = "SELECT * FROM public.file_analysis WHERE file_id = and type = ?;", nativeQuery = true)
    FileAnalysis findByFileId(String file_id, TypeAnalysis type);
}
