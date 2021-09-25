package preprocessingmining.com.example.preprocessingmining.model.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import preprocessingmining.com.example.preprocessingmining.model.Project;
import preprocessingmining.com.example.preprocessingmining.model.User;

import java.util.Calendar;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private String id;
    private String name;
    private String mail;
    private String password;
    private Calendar createdDate;
    private Calendar modifiedDate;

    private List<Project> projectList;

    public UserDto(User user, List<Project> projectList){
        this.id = user.getId();
        this.name = user.getName();
        this.mail = user.getMail();
        this.projectList = projectList;
    }

}
