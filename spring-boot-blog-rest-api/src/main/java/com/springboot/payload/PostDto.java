package com.springboot.payload;

import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Data
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;
    //i want to return set of comments along with my post
    private Set<CommentDto> comments;
}
