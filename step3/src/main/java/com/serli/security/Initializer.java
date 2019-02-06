package com.serli.security;

import com.serli.security.model.Comment;
import com.serli.security.model.CommentRepository;
import com.serli.security.model.User;
import com.serli.security.model.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

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
        User user1 = new User( "test", "test@test.com", "test");
        User user2 = new User("test1", "test1@test.com", "test1");
        User user3 = new User("test2", "test2@test.com", "test2");
        User user4 = new User("test3", "test3@test.com", "test3");
        Stream.of(user1,
                user2,
                user3,
                user4,
                new User("test4", "test4@test.com", "test4"),
                new User("test5", "test5@test.com", "test5")).forEach(user ->
                repository.save(user)
        );


        Stream.of(new Comment(null, "test de message", user1),
                new Comment(null, "test de message 2", user2),
                new Comment(null, "test de message 3", user3),
                new Comment(null, "test de message 4", user4)
        ).forEach(comment ->
                commentRepository.save(comment)
        );


        repository.findAll().forEach(System.out::println);
        commentRepository.findAll().forEach(System.out::println);
    }
}