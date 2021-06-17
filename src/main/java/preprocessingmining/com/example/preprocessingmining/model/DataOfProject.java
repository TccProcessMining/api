package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;


@NoArgsConstructor
@AllArgsConstructor
@Table("data_of_project")
public class DataOfProject {
    @PrimaryKey
    private String id;
    @Column(value = "project_id")
    private String projectId;
    private String name;
    private String type;
    private ByteBuffer file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ByteBuffer getFile() {
        return file;
    }

    public void setFile(ByteBuffer file) {
        this.file = file;
    }
}
