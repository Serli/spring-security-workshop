package com.serli.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serli.security.model.AuthToken;
import com.serli.security.model.User;
import com.serli.security.model.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;
import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    UserRepository userRepository;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint() {
                })
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/livredor").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/comments").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                //TODO
                .and()
                .authenticationProvider(getAuthProvider())
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                //TODO
        ;

    }

    @Bean
    public AuthenticationProvider getAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {

                UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

                String name = auth.getName();
                String password = auth.getCredentials().toString();


                UserDetails user = this.getUserDetailsService().loadUserByUsername(name);

                if (user == null || !bCryptPasswordEncoder().matches(password, user.getPassword())) {
                    throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
                }

                return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

            }
        };
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> {
            Objects.requireNonNull(username);
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return user;
        };
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:login.html");
        registry.addViewController("/livredor").setViewName("forward:livredor.html");
    }
}