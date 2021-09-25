package preprocessingmining.com.example.preprocessingmining.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import preprocessingmining.com.example.preprocessingmining.model.Field;
import preprocessingmining.com.example.preprocessingmining.model.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project,String> {

    @Query(value = "select * from projects where id in (select project_id from project_users  where user_id =  ?);", nativeQuery = true)
    List<Project> findProjectByUserID(String user_id);
}
