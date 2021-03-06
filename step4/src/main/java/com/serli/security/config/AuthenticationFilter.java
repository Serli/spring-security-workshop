package com.serli.security.config;

import com.serli.security.model.AuthTokenRepository;
import com.serli.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        String cookiePath = request.getContextPath() + "/";
        cookie.setPath(cookiePath);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        Cookie token = WebUtils.getCookie(request, authToken);
        if (token != null) {
            try {
                // Vérifier que le token correspond au existe en base et qu'il n'est pas expiré,
                // s'il est la valide le mettre dans le contexte
            } catch (Exception e) {
                deleteCookie(request, response, authToken);
                SecurityContextHolder.clearContext();
            }


        }
        chain.doFilter(request, response);
    }

}