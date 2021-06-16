package preprocessingmining.com.example.preprocessingmining.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import preprocessingmining.com.example.preprocessingmining.model.DataOfProject;

public interface DataOfProjectRepository extends CassandraRepository<DataOfProject,String> {
}
