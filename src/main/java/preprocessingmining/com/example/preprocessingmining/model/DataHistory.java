package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;

@NoArgsConstructor
@AllArgsConstructor
@Table("data_history")
@Getter
@Setter
public class DataHistory {
    @PrimaryKey
    private String id;
    private String data_id;
    private ByteBuffer file;
}
