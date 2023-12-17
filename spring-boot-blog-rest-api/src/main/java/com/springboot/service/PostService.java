package com.springboot.service;

import com.springboot.payload.PostDto;
import com.springboot.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    //List<PostDto> getAllPosts(int pageNo, int pageSize);
  //  PostResponse getAllPosts(int pageNo, int pageSize);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto findPostById(long postId);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(long id);

}
