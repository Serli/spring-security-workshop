package com.serli.security.config;

import com.serli.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    UserService userDetailsService;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint() {
        })
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/livredor").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/comments").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtTokenAuthenticationFilter(jwtConfig, userDetailsService), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
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

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:login.html");
        registry.addViewController("/livredor").setViewName("forward:livredor.html");
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}