package com.reseausocial.entreprise.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom", nullable = false)
    @NotBlank(message = "Nom obligatoire")
    private String nom;

    @Column(name = "prenom", nullable = false)
    @NotBlank(message = "Prenom obligatoire")
    private String prenom;

    @Column(name = "poste", nullable = false)
    @NotBlank(message = "Poste obligatoire")
    private String poste;

    @Column(name = "admin", columnDefinition = "boolean default false")
    private Boolean admin = false;

    @Column(name = "email", nullable = false, length = 255, unique = true)
    @NotBlank(message = "email obligatoire")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "email non valide")
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    @NotBlank(message = "Mot de passe obligatoire")
    @Length(min = 6, message = "votre mot de passe doit contenir au moin 6 caractères")
    private String password;


    /**
     * Constructeur par defaut
     */
    public User() {
    }

    /**
     * Constructeur surchargé sans ID
     * @param nom
     * @param prenom
     * @param poste
     * @param admin
     * @param email
     * @param password
     */
    public User(String nom, String prenom, String poste, Boolean admin, String email, String password) {
        setNom(nom);
        setPrenom(prenom);
        setPoste(poste);
        setAdmin(admin);
        setEmail(email);
        setPassword(password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", prenom='").append(prenom).append('\'');
        sb.append(", poste='").append(poste).append('\'');
        sb.append(", admin=").append(admin);
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
