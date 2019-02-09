package com.serli.security.web;

import com.serli.security.model.Comment;
import com.serli.security.model.CommentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentRepository commentRepository;

    public CommentController(CommentRepository repo) {
        this.commentRepository = repo;
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
    void delete(@RequestParam Long id){
        commentRepository.delete(new Comment(id, null, null));
    }
}
