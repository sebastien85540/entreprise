package com.reseausocial.entreprise.services;

import com.reseausocial.entreprise.entities.Article;
import com.reseausocial.entreprise.entities.Comment;
import com.reseausocial.entreprise.entities.User;
import com.reseausocial.entreprise.repositories.ArticleRepository;
import com.reseausocial.entreprise.repositories.CommentRepository;
import com.reseausocial.entreprise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    public Iterable<Comment> findComments(){
        return commentRepository.findAll();
    }

    public Comment findById(int id) throws Exception {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new Exception("Le commentaire n'a pas été trouvé");
        }
    }

    public Comment addComment(String contenu, int idArticle, int idUser){
        Comment com = new Comment();

        com.setContenu(contenu);

        Article art = articleRepository.findById(idArticle).get();
        art.setId(idArticle);
        com.setIdArticle(art);

        User us = userRepository.findById(idUser).get();
        us.setId(idUser);
        com.setIdUser(us);

        commentRepository.save(com);
        return com;
    }

    public Comment updateComment(int id, String contenu, int idArticle, int idUser) throws Exception {
        Comment co = this.findById(id);

        co.setContenu(contenu);

        Article ar = articleRepository.findById(idArticle).get();
        ar.setId(idArticle);
        co.setIdArticle(ar);

        User u = userRepository.findById(idUser).get();
        u.setId(idUser);
        co.setIdUser(u);

        commentRepository.save(co);
        return co;
    }

    public void delete(int id){
        commentRepository.deleteById(id);
    }
}
