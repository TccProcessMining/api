package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_users", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectUsers {
    @Id
    private String project_id;
    @Column(nullable = false)
    private String user_id;
}
