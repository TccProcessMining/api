package preprocessingmining.com.example.preprocessingmining.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "file_analysis", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class FileAnalysis {

    @Id
    private String id;
    private String file_id;
    @Column(nullable = false)
    private String project_id;
    @Column(nullable = false)
    private TypeAnalysis type;

    @Type(type = "jsonb")
    @Column(nullable = false)
    private String analysis;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Calendar createdDate;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Calendar modifiedDate;

}
