package preprocessingmining.com.example.preprocessingmining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import preprocessingmining.com.example.preprocessingmining.model.DTO.ProjectDto;
import preprocessingmining.com.example.preprocessingmining.model.DTO.UpdateValue;
import preprocessingmining.com.example.preprocessingmining.model.DataOfProject;
import preprocessingmining.com.example.preprocessingmining.model.Field;
import preprocessingmining.com.example.preprocessingmining.response.ResponseMessage;
import preprocessingmining.com.example.preprocessingmining.service.AnalysisService;
import preprocessingmining.com.example.preprocessingmining.service.DataOfProjectService;
import preprocessingmining.com.example.preprocessingmining.service.ProjectService;
import preprocessingmining.com.example.preprocessingmining.service.UserService;

import java.io.Serializable;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping(path = "/projects")
public class ProjectController implements Serializable {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private DataOfProjectService dataOfProjectService;
    @Autowired
    private AnalysisService analysisService;

    @GetMapping(path = "/{uuid}")
    public ResponseEntity project(@PathVariable("uuid") String uuid) {
        var project = projectService.findProjectByID(uuid);
        if (project == null) return ResponseEntity.notFound().build();
        var dataOfProjects = dataOfProjectService.listByProjectId(uuid);
        if(dataOfProjects.size() == 0)    return ResponseEntity.ok(project);
        var projectDto = new ProjectDto(project, dataOfProjects);
        return  ResponseEntity.ok(projectDto);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity save(@RequestBody String projectName) {
        var authentication = getContext().getAuthentication();
        var mail = authentication.getPrincipal().toString();
        final var user = userService.findUserByMail(mail);
        final var project = projectService.save(projectName, user);

        return ResponseEntity.ok(project);
    }

    @PostMapping("/{uuid}/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable("uuid") String uuid,
                                                      @RequestParam("file") MultipartFile file
                                                      ) {
        String message = "";
        try {
            dataOfProjectService.store(uuid, file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }

    @PostMapping("/{uuid}/analysis")
    public ResponseEntity analysis(@PathVariable("uuid") String projectId) {
        analysisService.analysis(projectId);
        return ResponseEntity.ok("");
    }

    @PostMapping("/{uuid}/applyActivitys")
    public ResponseEntity applyActivitys(@PathVariable("uuid") String projectId) {
        analysisService.applyActivitys(projectId);
        return ResponseEntity.ok("");
    }

    @PostMapping("/{uuid}/analyseDates")
    public ResponseEntity analyseDates(@PathVariable("uuid") String projectId) {
        analysisService.analyseDates(projectId);
        return ResponseEntity.ok("");
    }


    @PostMapping("/{uuid}/fixData")
    public ResponseEntity fixData(@PathVariable("uuid") String projectId) {
        analysisService.fixData(projectId);
        return ResponseEntity.ok("");
    }
    @PostMapping("/{uuid}/addFields")
    public ResponseEntity addFields(@PathVariable("uuid") String projectId,
                                    @RequestBody List<Field> fields) {
        fields.forEach(field -> field.setProject_id(projectId));
        return ResponseEntity.ok(projectService.saveFilds(fields));
    }

    @PostMapping("/{uuid}/removeColumn/{columnName}")
    public ResponseEntity removeColumn(@PathVariable("uuid") String uuid,@PathVariable("columnName") String comlumnName) {
        analysisService.removeColumn(uuid,comlumnName);
        return ResponseEntity.ok("");
    }

    @PostMapping("/{uuid}/mergeColumns/{columnName}")
    public ResponseEntity mergeColumns(@PathVariable("uuid") String uuid,@RequestBody String comlumnName) {
        //analysisService.mergeColumns(uuid,comlumnName);
        return ResponseEntity.ok("");
    }

    @PostMapping("/{uuid}/updateValue")
    public ResponseEntity updateValue(@PathVariable("uuid") String uuid,@RequestBody UpdateValue updateValue) {
        analysisService.updateValue(uuid,updateValue);
        return ResponseEntity.ok("");
    }

}