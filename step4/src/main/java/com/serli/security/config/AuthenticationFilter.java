package com.serli.security.config;

import com.serli.security.model.AuthToken;
import com.serli.security.model.AuthTokenRepository;
import com.serli.security.model.User;
import com.serli.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class AuthenticationFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final UserService userService;
    private final AuthTokenRepository authTokenRepository;
    private final String authToken;

    public AuthenticationFilter(AuthTokenRepository authTokenRepository, UserService userService, String authToken) {
        this.authTokenRepository = authTokenRepository;
        this.userService = userService;
        this.authToken = authToken;
    }

    public Optional<Cookie> readCookie(HttpServletRequest request, String key) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(c -> key.equals(c.getName()))
                    .findAny();
        }
        return Optional.empty();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        Optional<Cookie> token = readCookie(request, authToken);
        token.ifPresent((value) -> {
            try {
                Optional<AuthToken> byId = authTokenRepository.findById(value.getValue());
                byId.ifPresent((authTokenValue) -> {
                    if (authTokenValue.getExpiredDate().after(new Date())) {
                        Integer userId = authTokenValue.getUserId();
                        Optional<User> userOpt = userService.findById(userId);
                        userOpt.ifPresent(user -> {
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            log.info("url {} : authenticated user {}", request.getRequestURI(), user.getUsername());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        });
                    } else {
                        Optional<Cookie> cookie = readCookie(request, authToken);
                        cookie.ifPresent((c) -> {
                            c.setValue(null);
                            c.setMaxAge(0);
                            response.addCookie(c);
                        });
                        SecurityContextHolder.clearContext();
                    }
                });

            } catch (Exception e) {
                Optional<Cookie> cookie = readCookie(request, authToken);
                cookie.ifPresent((c) -> {
                    c.setValue(null);
                    c.setMaxAge(0);
                    response.addCookie(c);
                });
                SecurityContextHolder.clearContext();
            }
        });

        chain.doFilter(request, response);
    }

}