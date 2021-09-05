package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "file_fields_type", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Field {

    @Id
    private String id;
    @Column(nullable = false)
    private String file_id;
    @Column(nullable = false)
    private String project_id;
    @Column(nullable = false)
    private TypeField type;
    @Column(nullable = false)
    private String field;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Calendar createdDate;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Calendar modifiedDate;

    public Field(TypeField type, String field) {
        this.type = type;
        this.field = field;
    }

    public boolean isIdentifier(){
        return this.type.equals(TypeField.id);
    }

    public boolean isAtivity(){
        return this.type.equals(TypeField.ativity);
    }

    public boolean isCaseId(){
        return this.type.equals(TypeField.caseID);
    }
}
