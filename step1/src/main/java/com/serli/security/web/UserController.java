package com.serli.security.web;

import com.serli.security.model.User;
import com.serli.security.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Optional<User> byEmail = userRepository.findByEmail(username);

        log.info("l'utilisateur %s est connecté.", username);
        if (byEmail.isPresent() && byEmail.get().getPassword().equals(password)) {
            return ResponseEntity.ok().body(byEmail.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}