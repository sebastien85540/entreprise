package com.reseausocial.entreprise.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "commentaires")
public class Comment {
    @Id
    @Column(name = "comment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "contenu_commentaire", nullable = false)
    @NotBlank(message = "Votre commentaire doit avoir au moins 2 caractères")
    @Length(min = 2, message = "Votre commentaire doit contenir minimum 2 caractères")
    private String contenu;

    @ManyToOne
    @JoinColumn(name = "numero_article", nullable = false)
    private Article idArticle;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private User idUser;

    /**
     * Constructeur par defaut
     */
    public Comment() {
    }

    /**
     * Constructeur surchargé sans ID
     * @param contenu
     * @param idArticle
     * @param idUser
     */
    public Comment(String contenu, Article idArticle, User idUser) {
        setContenu(contenu);
        setIdArticle(idArticle);
        setIdUser(idUser);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Article getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Article idArticle) {
        this.idArticle = idArticle;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("id=").append(id);
        sb.append(", contenu='").append(contenu).append('\'');
        sb.append(", idArticle=").append(idArticle);
        sb.append(", idUser=").append(idUser);
        sb.append('}');
        return sb.toString();
    }
}
