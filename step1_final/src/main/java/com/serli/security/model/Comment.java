package com.serli.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 4000)
    private String text;

    @OneToOne
    private User user;
}
