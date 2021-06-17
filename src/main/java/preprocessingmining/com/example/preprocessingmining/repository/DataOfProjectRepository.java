package preprocessingmining.com.example.preprocessingmining.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import preprocessingmining.com.example.preprocessingmining.model.DataOfProject;

import java.util.List;
import java.util.UUID;

public interface DataOfProjectRepository extends CassandraRepository<DataOfProject, UUID> {
    @Query("SELECT * FROM data_of_project WHERE project_id = ?0 ALLOW FILTERING")
    List<DataOfProject> findByProjectId(String projectId);
}
