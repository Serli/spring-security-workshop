package com.serli.security.web;

import com.serli.security.config.JwtConfig;
import com.serli.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);


//    @Autowired
//    private AuthenticationManager authenticationManager;

    @GetMapping("/current")
    ResponseEntity<?> getUserConnected() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(user);
    }


//    @PostMapping("/login")
//    public ResponseEntity<AuthToken> login(@RequestBody User userLogin, HttpServletResponse resp) {
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        userLogin.getUsername(),
//                        userLogin.getPassword()
//                )
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final User user = (User) authentication.getPrincipal();
//        Claims claims = Jwts.claims().setSubject(user.getUsername());
////        if (user.isAdmin()) {
////            claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
////        }
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenUtil.getExpiration()))
//                .signWith(SignatureAlgorithm.HS256, jwtTokenUtil.getSecret())
//                .compact();
//        return ResponseEntity.ok(new AuthToken(token));
//
//    }


}


