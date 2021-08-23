package preprocessingmining.com.example.preprocessingmining.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import preprocessingmining.com.example.preprocessingmining.model.Field;

import java.util.List;

@Repository
public interface FileFieldsTypeRepository extends CrudRepository<Field,String> {
    @Query(value = "SELECT * FROM public.file_fields_type WHERE project_id = ?;", nativeQuery = true)
    List<Field> findByProjectID(String project_id);

    @Query(value = "SELECT * FROM public.file_fields_type WHERE project_id = ? AND file_id = ?;", nativeQuery = true)
    List<Field>  findByProjectIDAndFileID(String projectID, String fileId);
}
