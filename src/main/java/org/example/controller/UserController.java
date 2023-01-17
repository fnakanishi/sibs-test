package org.example.controller;

import org.example.controller.request.UserRequest;
import org.example.controller.response.MessageResponse;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserService userService;

    @PostMapping("/create")
    public ResponseEntity create(@Valid @RequestBody UserRequest request) {
        String name = request.getName();
        String email = request.getEmail();

        if (userService.existByName(name)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User name is already in the database!"));
        }

        User user = userService.save(name, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        Optional<User> userOpt = userService.findById(id);
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User could not be found!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userOpt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UserRequest request) {
        String name = request.getName();
        String email = request.getEmail();

        Optional<User> userOpt = userService.findById(id);
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User could not be found!"));
        }

        User user = userService.update(userOpt.get(), name, email);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<User> userOpt = userService.findById(id);
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User could not be found!"));
        }

        userService.delete(userOpt.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list-all")
    public ResponseEntity listAll() {
        List<User> userList = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }
}
