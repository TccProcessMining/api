package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;


@NoArgsConstructor
@AllArgsConstructor
@Table("data_of_project")
@Getter
@Setter
public class DataOfProject {
    @PrimaryKey
    private String id;
    @Column(value = "project_id")
    private String projectId;
    private String name;
    private String type;
    private ByteBuffer file;
}
