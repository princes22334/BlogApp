package com.springboot.service;

import com.springboot.entity.Post;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.payload.PostDto;
import com.springboot.payload.PostResponse;
import com.springboot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
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
        return convertPost(savedPost);
    }

   /* @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize) {        //refer notes for it
        //create page instance
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> posts = postRepository.findAll(pageable);

        //get content for page Object
        List<Post> allPost = posts.getContent();

        // List<Post> allPosts = postRepository.findAll();
        //Converts allPosts into PostDto Object
        //need to use stream api to convert all post into dtos
        return allPost.stream().map(post -> convertPost(post)).collect(Collectors.toList());
    }*/

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize) {
        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize);

      Page<Post> posts = postRepository.findAll(pageable);
       // it returns a Page object containing a subset of entities based on the pagination information.

        //get content for page object
        List<PostDto> content = posts.stream().map(post -> convertPost(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }

        @Override
    public PostDto findPostById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id: "+postId+" Not Available"));
        //here we use supplier Functional Interface because we are not providing any input but still generating output
        return convertPost(post);

    }
    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //first we need to check that the postId is available or not
       Post post = postRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Post id: "+id+" Not Available"));

       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());
      Post updatedPost = postRepository.save(post);
        PostDto updatedDto = convertPost(updatedPost);
        return updatedDto;
    }

    @Override
    public void deletePostById(long id) {
        //First I need to retrieve entity by id just to check id is available or not
        postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + ": Id Not Found"));
        postRepository.deleteById(id); //or postRepository.delete(post)
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
    //Convert Post into DTO
    public PostDto convertPost(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }
}
