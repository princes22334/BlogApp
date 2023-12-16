package com.springboot.service;

import com.springboot.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();
    PostDto findPostById(long postId);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(long id);

}
