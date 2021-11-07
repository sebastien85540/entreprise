package com.reseausocial.entreprise.security;

import com.reseausocial.entreprise.entities.User;
import com.reseausocial.entreprise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Utilisateur inconnu");
        }
        List<GrantedAuthority> auth = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
        GrantedAuthority authority1 = new SimpleGrantedAuthority("USER");
        if (user.getAdmin()){
            auth.add(authority);
        } else {
            auth.add(authority1);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), auth);
    }
}
