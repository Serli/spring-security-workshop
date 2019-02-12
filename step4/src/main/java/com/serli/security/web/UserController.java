package com.serli.security.web;

import com.serli.security.model.AuthToken;
import com.serli.security.model.AuthTokenRepository;
import com.serli.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
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
    ResponseEntity<User> getUserConnected() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(null);
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
//        tokenCookie.setHttpOnly(true);
            tokenCookie.setPath("/");
//        tokenCookie.setSecure(true);
            tokenCookie.setMaxAge(expiredTime);
//        tokenCookie.setDomain("localhost");
            response.addCookie(tokenCookie);

            response.sendRedirect("/");
        } catch (Exception e) {
            response.sendRedirect("/#!/login/error");
        }

    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<AuthToken> byUserId = authTokenRepository.findByUserId(user.getId());

        byUserId.forEach((authToken) -> {
            authTokenRepository.delete(authToken);
            log.info("suppression de la session : {} pour l'utilisateur {}", authToken.getToken(), user.getUsername());
        });
        response.sendRedirect("/login");
    }


}