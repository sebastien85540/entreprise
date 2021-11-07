package com.reseausocial.entreprise.services;

import com.reseausocial.entreprise.entities.User;
import com.reseausocial.entreprise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Iterable<User> findUsers(){
        return userRepository.findAll();
    }

    public User findById(int id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("L'utilisateur n'a pas été trouvé");
        }
    }

    public User findByEmail(String email){
        User user = userRepository.findByEmail(email);
        return user;
    }

    public User addUser(String nom, String prenom, String poste,Boolean admin, String email, String password){
        User u = new User();
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setPoste(poste);
        u.setAdmin(admin);
        u.setEmail(email);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(u);
        return u;
    }

    public User updateUser(int id, String nom, String prenom, String poste, Boolean admin, String email, String password) throws Exception {
        User us = this.findById(id);
        us.setNom(nom);
        us.setPrenom(prenom);
        us.setPoste(poste);
        us.setAdmin(admin);
        us.setEmail(email);
        us.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(us);
        return us;
    }

    public void delete(int id){
        userRepository.deleteById(id);
    }
}
