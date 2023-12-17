package com.springboot.controller;

import com.springboot.entity.Comment;
import com.springboot.payload.CommentDto;
import com.springboot.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //create Rest api for create comment
    // http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable("postId") long id,
            @RequestBody CommentDto dto){
        CommentDto comment = commentService.createComment(id, dto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    //create rest api to get all comments
   // http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}")
    public List<CommentDto> getCommentByPostId(@PathVariable("postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }
















}
