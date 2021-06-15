package preprocessingmining.com.example.preprocessingmining.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import preprocessingmining.com.example.preprocessingmining.model.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project,String> {
}
