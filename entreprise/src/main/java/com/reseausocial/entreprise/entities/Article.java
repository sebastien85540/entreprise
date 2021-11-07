package com.reseausocial.entreprise.entities;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="articles")
public class Article {
    @Id
    @Column(name = "article_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titre_article", nullable = false, length = 255)
    @NotBlank(message = "Titre de l'article obligatoire")
    private String titre;

    @Column(name = "sujet_article", nullable = false, length = 255)
    @NotBlank(message = "Sujet obligatoire")
    private String sujet;

    @Column(name = "contenu", nullable = false, length = 10000)
    @NotBlank(message = "Contenu obligatoire")
    private String contenu;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    @NotBlank(message = "Utilisateur obligatoire")
    private User user;

    /**
     * constructeur par defaut
     */
    public Article() {
    }

    /**
     * Constructeur surchargé sans ID
     * @param titre
     * @param sujet
     * @param contenu
     * @param user
     */
    public Article(String titre, String sujet, String contenu, User user) {
        setTitre(titre);
        setSujet(sujet);
        setContenu(contenu);
        setUser(user);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Article{");
        sb.append("id=").append(id);
        sb.append(", titre='").append(titre).append('\'');
        sb.append(", sujet='").append(sujet).append('\'');
        sb.append(", contenu='").append(contenu).append('\'');
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
