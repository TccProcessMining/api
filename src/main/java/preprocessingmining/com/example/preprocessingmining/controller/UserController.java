package preprocessingmining.com.example.preprocessingmining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import preprocessingmining.com.example.preprocessingmining.model.User;
import preprocessingmining.com.example.preprocessingmining.service.UserService;

import java.io.Serializable;

@RestController
@RequestMapping(path = "/users")
public class UserController implements Serializable {
    @Autowired
    private UserService userService;


    @GetMapping(path = "/{id}")
    public ResponseEntity user(@PathVariable("id") String id) {
        var user = userService.findUserByID(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }
}