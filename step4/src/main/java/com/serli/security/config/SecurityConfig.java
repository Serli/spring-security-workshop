package com.serli.security.config;

import com.serli.security.model.AuthToken;
import com.serli.security.model.AuthTokenRepository;
import com.serli.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@Controller
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    UserService userDetailsService;

    @Autowired
    AuthTokenRepository authTokenRepository;

    @Value("${com.serli.auth.token}")
    private String authToken;


    @Value("${com.serli.csrf.token}")
    private String csrfCookieTokenName;

    @Value("${com.serli.csrf.header.token}")
    private String csrfHeaderTokenName;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/img/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
//                .accessDeniedHandler(getAccessDeniedHandler())
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/login","/", "/login", "/livredor", "/error").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/comments").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new AuthenticationFilter(authTokenRepository, userDetailsService, authToken), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(getCsrfFilter(), CsrfFilter.class)
                .addFilterAfter(getCsrfHeaderFilter(), SessionManagementFilter.class)
                .logout()
                .logoutUrl("/api/user/logout")
                .logoutSuccessHandler(getLogoutSuccessHandler())
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies(authToken, csrfCookieTokenName)
                .and()
                .csrf()
                .csrfTokenRepository(new CookieCsrfTokenRepository())
        ;


    }

    private LogoutSuccessHandler getLogoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            private final Logger log = LoggerFactory.getLogger(this.getClass());

            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse httpServletResponse, Authentication authentication) {
                Cookie token = WebUtils.getCookie(request, authToken);
                if (token != null) {
                    Optional<AuthToken> byUserId = authTokenRepository.findById(token.getValue());

                    byUserId.ifPresent((authToken) -> {
                        authTokenRepository.delete(authToken);
                        log.info("suppression de la session : {}", authToken.getToken());
                    });
                }
            }
        };
    }

    private OncePerRequestFilter getCsrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrf != null && response.getStatus() == HttpStatus.OK.value()) {
                    String token = csrf.getToken();
                    response.setHeader(csrfHeaderTokenName, token);
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    private OncePerRequestFilter getCsrfFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, csrfCookieTokenName);
                    String token = csrf.getToken();
                    if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                        cookie = new Cookie(csrfCookieTokenName, token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> {
            UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

            String name = auth.getName();
            String password = auth.getCredentials()
                    .toString();


            UserDetails user = userDetailsService.loadUserByUsername(name);

            if (user == null || !bCryptPasswordEncoder().matches(password, user.getPassword())) {
                throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
            }

            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

        };
    }

//    @Bean
//    AccessDeniedHandler getAccessDeniedHandler() {
//        return new AccessDeniedHandler() {
//            @Override
//            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
//                if (!response.isCommitted()) {
//                    if(e instanceof MissingCsrfTokenException){
//                        response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
//                    }else {
//                        response.sendError(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase());
//                    }
//                }
//            }
//        };
//    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        Cookie cookie = WebUtils.getCookie(request, csrfCookieTokenName);
        if (cookie == null) {
            CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            if (csrf != null) {
                String token = csrf.getToken();
                cookie = new Cookie(csrfCookieTokenName, token);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        model.addAttribute("csrfToken", cookie.getValue());
        return "login";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        return login(request, response, model);
    }

    @GetMapping("/livredor")
    public String livredor(HttpServletRequest request, Model model) {
        Cookie cookie = WebUtils.getCookie(request, csrfCookieTokenName);
        model.addAttribute("csrfToken", cookie.getValue());
        return "livredor";
    }

}