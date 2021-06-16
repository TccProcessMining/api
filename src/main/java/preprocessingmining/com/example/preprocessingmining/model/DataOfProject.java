package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Table("data_of_project")
public class DataOfProject {
    @PrimaryKey
    private UUID uuid;
    private String name;
    private String type;
    private ByteBuffer file;
}
