package com.springboot.controller;

import com.springboot.payload.PostDto;
import com.springboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    //using interface as a looseCoupling(Classes can be used as tight coupling)
    //configuring class as a Spring bean
    private PostService postService;

    @Autowired                          //Optional here
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //Create api for post Creation
    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto dto){
        PostDto Response = postService.createPost(dto);
        return new ResponseEntity<>(Response, HttpStatus.CREATED);
    }
}
