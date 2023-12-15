package com.springboot.service;

import com.springboot.entity.Post;
import com.springboot.payload.PostDto;
import com.springboot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;
    //Constructor based dependencies injection
    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
       //convert PostDto  into Post
        Post post = convertPostDto(postDto);
        Post savedPost = postRepository.save(post);

        //Convert Post into PostDto to return to Client
        PostDto postResponse = convertPost(savedPost);
        return postResponse;
    }

    //Convert PostDto into Post Entity
    public Post convertPostDto(PostDto postDto){
        Post post = new Post();
        //post.setId(postDto.getId());  no need because id is auto increment
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
    //Convert Post into PostDto
    public PostDto convertPost(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }
}
