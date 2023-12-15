package com.springboot.service;

import com.springboot.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
