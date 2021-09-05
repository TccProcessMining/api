package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "projects", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project implements Serializable {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Calendar createdDate;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Calendar modifiedDate;

    public Project(String name){
        this.name = name;
    }

}
