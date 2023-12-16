package com.springboot.controller;

import com.springboot.payload.PostDto;
import com.springboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    //using interface as a looseCoupling(Classes can be used as tight coupling)
    //configuring class as a Spring bean
    private PostService postService;
    private long postId;

    @Autowired                          //Optional here
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //Create api for post Creation
    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto dto){
        PostDto Response = postService.createPost(dto);
        return new ResponseEntity<>(Response, HttpStatus.CREATED);
    }

    //create api to get all posts
    @GetMapping("/getAll")
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    //create api to get postDto by id
    @GetMapping("/getPost/{id}")
    public ResponseEntity<PostDto> getPostDtoById(@PathVariable("id") long id){
        PostDto postById = postService.findPostById(id);
        return new ResponseEntity<>(postById, HttpStatus.OK);
    }
    //Create rest api to update postDto
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updatePostDtoById(@RequestBody PostDto postDto,
                                                     @PathVariable(name="id") long id){
        PostDto updatedDto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }
    //create rest api to delete posts by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Id deleted Successfully", HttpStatus.OK);
    }
}
