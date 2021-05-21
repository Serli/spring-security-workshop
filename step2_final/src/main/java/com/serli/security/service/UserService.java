package com.serli.security.service;

import com.serli.security.model.User;
import com.serli.security.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Objects.requireNonNull(username);
        Optional<User> byEmail = userRepository.findByEmail(username);
        User user = byEmail
//                .orElse(new User());
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user;
    }

}
