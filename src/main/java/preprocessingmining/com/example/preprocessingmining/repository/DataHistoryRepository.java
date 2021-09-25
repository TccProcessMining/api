package preprocessingmining.com.example.preprocessingmining.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import preprocessingmining.com.example.preprocessingmining.model.DataHistory;
import preprocessingmining.com.example.preprocessingmining.model.DataOfProject;

import java.util.UUID;

public interface DataHistoryRepository extends CassandraRepository<DataHistory, UUID> {
}
