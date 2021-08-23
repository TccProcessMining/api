package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "file_fields_type", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class Field {

    @Id
    private String id;
    @Column(nullable = false)
    private String file_id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public TypeField getType() {
        return type;
    }

    public void setType(TypeField type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public Calendar getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
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
