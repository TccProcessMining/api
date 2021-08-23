package preprocessingmining.com.example.preprocessingmining.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import preprocessingmining.com.example.preprocessingmining.model.Field;

import java.util.List;

@Service
public class FildsService {
    @Autowired
    private ProjectService projectService;

    public String identifierColumn(String projectId, String fileId) {
        List<Field> fields = projectService.getFilds(projectId, fileId);
        for (Field i : fields)
            if (i.isIdentifier())
                return i.getField();
        return null;
    }

    public String ativityColumn(String projectId, String fileId) {
        List<Field> fields = projectService.getFilds(projectId,fileId);
        for (Field i : fields)
            if (i.isAtivity())
                return i.getField();
        return null;
    }
    public String caseIdColumn(String projectId, String fileId) {
        List<Field> fields = projectService.getFilds(projectId,fileId);
        for (Field i : fields)
            if (i.isCaseId())
                return i.getField();
        return null;
    }
}
