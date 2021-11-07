package com.reseausocial.entreprise.api;

import com.reseausocial.entreprise.entities.User;
import com.reseausocial.entreprise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "", produces = "application/json")
    public Iterable<User> findAll(){
        return userService.findUsers();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity findById(@PathVariable("id") int id){
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok().body(user);
        } catch (Exception e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity addUser(@Valid @RequestBody User user){
        User createUser = null;
        try {
            createUser = userService.addUser(user.getNom(), user.getPrenom(), user.getPoste(), user.getAdmin(), user.getEmail(), user.getPassword());
        } catch (Exception e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
            try{
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createUser.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(createUser);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity editUser(@Valid @RequestBody User user, @PathVariable("id") int id){
        User updateUser = null;
        try {
            updateUser = userService.updateUser(id, user.getNom(), user.getPrenom(), user.getPoste(), user.getAdmin(), user.getEmail(), user.getPassword());
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        try{
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(updateUser)
                    .toUri();
            return ResponseEntity.created(uri).body(updateUser);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") int id){
        try {
            userService.delete(id);
            return ResponseEntity.ok().body("La suppression à réussie");
        } catch (Exception e){
            return ResponseEntity.status(404).body("La suppression à échouée : " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
