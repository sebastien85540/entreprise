package com.reseausocial.entreprise.api;

import com.reseausocial.entreprise.entities.Comment;
import com.reseausocial.entreprise.services.CommentService;
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
@RequestMapping("/api/comment")
public class CommentAPIController {

    @Autowired
    private CommentService commentService;

    @GetMapping(path = "", produces = "application/json")
    public Iterable<Comment> findAll(){
        return commentService.findComments();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Comment> findCommentById(@PathVariable("id") int id){
        try {
            Comment comment = commentService.findById(id);
            return ResponseEntity.ok().body(comment);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<Comment> addComment(@Valid @RequestBody Comment comment){
        try {
            Comment createComment = commentService.addComment(comment.getContenu(), comment.getIdArticle().getId(), comment.getIdUser().getId());
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createComment.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(createComment);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Comment> editComment(@Valid @RequestBody Comment comment, @PathVariable("id") int id){
        try {
            Comment updateComment = commentService.updateComment(id, comment.getContenu(), comment.getIdArticle().getId(), comment.getIdUser().getId());
            return ResponseEntity.ok().body(updateComment);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable("id") int id){
        try {
            commentService.delete(id);
            return ResponseEntity.ok().body(null);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
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
