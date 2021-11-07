package com.reseausocial.entreprise.api;

import com.reseausocial.entreprise.entities.Article;
import com.reseausocial.entreprise.services.ArticleService;
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
@RequestMapping("/api/article")
public class ArticleAPIController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(path = "", produces = "application/json")
    public Iterable<Article> findAll(){
        return articleService.findArticles();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity findById(@PathVariable("id") int id){
        try {
            Article article = articleService.findById(id);
            return ResponseEntity.ok().body(article);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity addArticle(@Valid @RequestBody Article article){
        Article createArticle = null;
        try{
            createArticle = articleService.addArticle(article.getTitre(), article.getSujet(), article.getContenu(), article.getUser().getId());
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createArticle.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(createArticle);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity editArticle(@Valid @RequestBody Article article, @PathVariable("id") int id){
        Article editArticle = null;
        try {
            editArticle = articleService.updateArticle(id, article.getTitre(), article.getSujet(), article.getContenu(), article.getUser().getId());
        } catch (Exception e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
            try{
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(editArticle)
                    .toUri();

            return ResponseEntity.created(uri).body(editArticle);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteArticle(@PathVariable("id") int id){
        try {
            articleService.delete(id);
            return ResponseEntity.ok().body("Suppression éffectuée");
        } catch (Exception e){
            return ResponseEntity.status(404).body("Suppression échouée : " + e.getMessage());
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
