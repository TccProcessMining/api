package preprocessingmining.com.example.preprocessingmining.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_users", schema = "public")
public class ProjectUsers {
    @Id
    private String project_id;
    @Column(nullable = false)
    private String user_id;

    public ProjectUsers(String project_id, String user_id) {
        this.project_id = project_id;
        this.user_id = user_id;
    }

    public ProjectUsers() {
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
