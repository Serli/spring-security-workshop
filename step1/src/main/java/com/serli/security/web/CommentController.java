package com.serli.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serli.security.model.Comment;
import com.serli.security.model.CommentRepository;
import com.serli.security.model.User;
import com.serli.security.model.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentRepository commentRepository;
    private UserRepository userRepository;

    public CommentController(CommentRepository repo, UserRepository userRepository) {
        this.commentRepository = repo;
        this.userRepository = userRepository;
    }

    @GetMapping
    Collection<Comment> comments() {
        return commentRepository.findAll();
    }

    @PostMapping
    void save(@RequestBody Comment comment) {
        commentRepository.save(comment);
    }

    @DeleteMapping
    void delete(@RequestParam Long id, HttpServletRequest request, HttpServletResponse res) {
        if (request.getCookies() != null) {
            Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                    .filter(c -> "USER".equals(c.getName()))
                    .findAny();
            cookie.ifPresent((c) -> {
                ObjectMapper mapper = new ObjectMapper();
                User user = null;
                try {
                    user = mapper.readValue(URLDecoder.decode(c.getValue()), User.class);
                } catch (IOException e) {

                    try {
                        res.sendError(HttpStatus.UNAUTHORIZED.value());
                    } catch (IOException ex) {
                    }
                }
                Optional<User> byId = userRepository.findById(user.getId());
                byId.ifPresent(u -> {
                    if (u.isAdmin()) {
                        commentRepository.delete(new Comment(id, null, null));
                        return;
                    }

                    try {
                        res.sendError(HttpStatus.UNAUTHORIZED.value());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

            });
        }
    }
}
