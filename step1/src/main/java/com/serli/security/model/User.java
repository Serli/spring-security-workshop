package com.serli.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean admin;

    public User(String name, String email, String password){
        this.name=name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, boolean isAdmin){
        this(name, email, password);
        this.admin=isAdmin;
    }

}