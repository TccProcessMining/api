package preprocessingmining.com.example.preprocessingmining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import preprocessingmining.com.example.preprocessingmining.response.ResponseMessage;
import preprocessingmining.com.example.preprocessingmining.service.AnalysisService;
import preprocessingmining.com.example.preprocessingmining.service.DataOfProjectService;
import preprocessingmining.com.example.preprocessingmining.service.ProjectService;
import preprocessingmining.com.example.preprocessingmining.service.UserService;

import java.io.Serializable;

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
        var user = projectService.findProjectByID(uuid);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
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
                                                      @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            dataOfProjectService.store(uuid,file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @PostMapping("/{uuid}/analysis")
    public ResponseEntity analysis(@PathVariable("uuid") String uuid){

        analysisService.analysis(uuid);
        return ResponseEntity.ok("");
    }


}