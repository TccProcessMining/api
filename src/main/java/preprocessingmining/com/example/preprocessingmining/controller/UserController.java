package preprocessingmining.com.example.preprocessingmining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import preprocessingmining.com.example.preprocessingmining.model.DTO.UserDto;
import preprocessingmining.com.example.preprocessingmining.model.Project;
import preprocessingmining.com.example.preprocessingmining.model.User;
import preprocessingmining.com.example.preprocessingmining.service.ProjectService;
import preprocessingmining.com.example.preprocessingmining.service.UserService;

import java.io.Serializable;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping(path = "/users")
public class UserController implements Serializable {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @GetMapping(path = "/{uuid}")
    public ResponseEntity user(@PathVariable("uuid") String uuid) {
        var user = userService.findUserByID(uuid);
        if (user == null) return ResponseEntity.notFound().build();
        var projectList = projectService.findProjectByUserID(uuid);
        return ResponseEntity.ok(new UserDto(user, projectList));
    }

    @PostMapping(path = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }


    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity save(@PathVariable("name") String projectName) {
        var authentication = getContext().getAuthentication();
        var mail = authentication.getPrincipal().toString();
        final var user = userService.findUserByMail(mail);
        if (user == null) return ResponseEntity.notFound().build();
        var projectList = projectService.findProjectByUserID(user.getId());
        return ResponseEntity.ok(new UserDto(user, projectList));
    }

}