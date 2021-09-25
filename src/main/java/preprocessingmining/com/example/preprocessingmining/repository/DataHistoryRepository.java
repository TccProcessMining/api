package preprocessingmining.com.example.preprocessingmining.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import preprocessingmining.com.example.preprocessingmining.model.DataHistory;

import java.util.UUID;

public interface DataHistoryRepository extends CassandraRepository<DataHistory, UUID> {
}
