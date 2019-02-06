package com.serli.security.web;

import com.serli.security.model.Comment;
import com.serli.security.model.CommentRepository;
import com.serli.security.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentRepository commentRepository;

    public CommentController(CommentRepository repo){
        this.commentRepository = repo;
    }

    @GetMapping("/comments")
    Collection<Comment> users() {
        return commentRepository.findAll();
    }

    @PostMapping("/comment")
    void save(@RequestBody Comment comment) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setUser(principal);
        commentRepository.save(comment);
    }
}
