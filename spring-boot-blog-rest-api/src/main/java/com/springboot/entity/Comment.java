package com.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "body")
    private String body;

    //@Many to One = so that multiple comments belong to one post(biDirectional relationship)
    //FetchType.LAZY tells hibernate to only fetch the related entities from the database when you use the relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false) //to define foreign key in comments table
    private Post post;


}
