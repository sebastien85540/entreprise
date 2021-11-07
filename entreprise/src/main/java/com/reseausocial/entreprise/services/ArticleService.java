package com.reseausocial.entreprise.services;

import com.reseausocial.entreprise.entities.Article;
import com.reseausocial.entreprise.entities.User;
import com.reseausocial.entreprise.repositories.ArticleRepository;
import com.reseausocial.entreprise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    public Iterable<Article> findArticles(){
        return articleRepository.findAll();
    }

    public Article findById(int id) throws Exception {
        Optional<Article> art = articleRepository.findById(id);
        if (art.isPresent()) {
            return art.get();
        } else {
            throw new Exception("L'article n'éxiste pas");
        }
    }

    public Article addArticle(String titre, String sujet, String contenu, Integer user){
        Article art = new Article();

        art.setTitre(titre);
        art.setSujet(sujet);
        art.setContenu(contenu);

        User u = userRepository.findById(user).get();
        u.setId(user);

        art.setUser(u);
        articleRepository.save(art);
        return art;
    }

    public Article updateArticle(int id, String titre, String sujet, String contenu, Integer user) throws Exception {
        Article ar = this.findById(id);

        ar.setTitre(titre);
        ar.setSujet(sujet);
        ar.setContenu(contenu);
        User us = userRepository.findById(user).get();
        us.setId(user);

        ar.setUser(us);
        articleRepository.save(ar);
        return ar;
    }

    public void delete(int id){
        articleRepository.deleteById(id);
    }

}
