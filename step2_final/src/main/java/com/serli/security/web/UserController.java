package com.serli.security.web;

import com.serli.security.model.User;
import com.serli.security.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("")
class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/api/user/current")
    ResponseEntity<User> getUserConnected() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(null);
        return ResponseEntity.ok().body(user);
    }
}