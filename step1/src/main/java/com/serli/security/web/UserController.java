package com.serli.security.web;

import com.serli.security.model.User;
import com.serli.security.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserRepository userRepository;
    private EntityManager em;
    private JdbcTemplate jdbcTemplate;

    public UserController(UserRepository userRepository, EntityManager em, JdbcTemplate template) {
        this.em = em;
        this.userRepository = userRepository;
        this.jdbcTemplate = template;
    }




    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        String sql = "Select * from user where email='" + username + "' and password='" + password + "'";
        List<User> maps = jdbcTemplate.query(sql, (resultSet, i) -> new User(resultSet.getInt(1),
                resultSet.getString(3),
                resultSet.getString(2),
                resultSet.getString(4)
        ));


        if (maps != null && maps.size() > 0) {
            return ResponseEntity.ok().body(maps.get(0));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}