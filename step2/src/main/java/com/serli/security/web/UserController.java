package com.serli.security.web;

import com.serli.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/current")
    User getUserConnected() {
        //TODO
        return null;
    }
}