package com.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
//unique constrains we use to make table unique
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title", unique = true, nullable=false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name="content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
}
