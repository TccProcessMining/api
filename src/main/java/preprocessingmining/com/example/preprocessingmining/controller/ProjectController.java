package preprocessingmining.com.example.preprocessingmining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(path = "/{id}")
    public ResponseEntity project(@PathVariable("id") String id) {
        var user = projectService.getUserByID(id);
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
}