package com.serli.security.web;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@RestController
@RequestMapping("/api/comments")
public class CommentController {


    @GetMapping
    Collection<?> comments() {
        return null;
    }

    @PostMapping
    void save(@RequestBody Object comment) {
    }

    @DeleteMapping
    void delete(@RequestParam Long id, HttpServletRequest request, HttpServletResponse res) {
    }
}
