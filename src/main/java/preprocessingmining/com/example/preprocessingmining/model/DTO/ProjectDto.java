package preprocessingmining.com.example.preprocessingmining.model.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import preprocessingmining.com.example.preprocessingmining.model.DataOfProject;
import preprocessingmining.com.example.preprocessingmining.model.Project;

import java.util.Calendar;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDto {
    private String id;
    private String name;
    private Calendar createdDate;
    private Calendar modifiedDate;

    private List<DataOfProject> dataOfProjects;

    public ProjectDto(Project project, List<DataOfProject> dataOfProjects){
        this.id = project.getId();
        this.name = project.getName();
        this.createdDate = project.getCreatedDate();
        this.modifiedDate = project.getModifiedDate();
        this.dataOfProjects = dataOfProjects;
    }

}
