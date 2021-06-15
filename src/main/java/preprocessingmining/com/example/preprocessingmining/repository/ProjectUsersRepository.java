package preprocessingmining.com.example.preprocessingmining.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import preprocessingmining.com.example.preprocessingmining.model.ProjectUsers;

@Repository
public interface ProjectUsersRepository extends CrudRepository<ProjectUsers, String> {
}
