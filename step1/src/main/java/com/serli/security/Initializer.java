package com.serli.security;

import com.serli.security.model.Comment;
import com.serli.security.model.CommentRepository;
import com.serli.security.model.User;
import com.serli.security.model.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class Initializer implements CommandLineRunner {

    private final UserRepository repository;
    private final CommentRepository commentRepository;

    public Initializer(UserRepository repository, CommentRepository repo) {
        this.repository = repository;
        this.commentRepository = repo;
    }

    @Override
    public void run(String... strings) {

        for (int i = 1; i < 5; i++) {
            User user = new User("test" + i, "test" + i + "@test.com", "test" + i);
            repository.save(user);
            Comment comment = new Comment(null, "test de message "+i, user);
            commentRepository.save(comment);
        }

    }
}