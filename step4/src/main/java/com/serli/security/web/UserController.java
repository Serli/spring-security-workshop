package com.serli.security.web;

import com.serli.security.model.AuthToken;
import com.serli.security.model.AuthTokenRepository;
import com.serli.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Value("${com.serli.auth.token}")
    private String authToken;

    @Value("${com.serli.auth.expired}")
    private int expiredTime;


    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/current")
    ResponseEntity<User> getUserConnected(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok().body(user);
    }


    @PostMapping("/login")
    public void login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final User user = (User) authentication.getPrincipal();
            String sessionId = UUID.randomUUID().toString();
            Date expiredDate = new Date(System.currentTimeMillis() + expiredTime);
            AuthToken token = new AuthToken(sessionId, user.getId(), expiredDate);
            authTokenRepository.save(token);
            log.info("new session : {} expired in {} user {}", token.getToken(), token.getExpiredDate().toString(), user.getUsername());

            Cookie tokenCookie = new Cookie(authToken, token.getToken());
            tokenCookie.setPath("/");
            tokenCookie.setHttpOnly(true);
            tokenCookie.setMaxAge(expiredTime);
            response.addCookie(tokenCookie);
            response.sendRedirect("/livredor");
        } catch (Exception e) {
            response.sendError(HttpStatus.LOCKED.value());
        }

    }


}