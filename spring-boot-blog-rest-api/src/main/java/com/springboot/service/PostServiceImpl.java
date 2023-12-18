package com.springboot.service;

import com.springboot.entity.Post;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.payload.PostDto;
import com.springboot.payload.PostResponse;
import com.springboot.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private ModelMapper modelMapper;

    //Constructor based dependencies injection
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
       //convert PostDto  into Post
        Post post = maptoEntity(postDto);
        Post savedPost = postRepository.save(post);

        //Convert Post into PostDto to return to Client
        return mapToDto(savedPost);
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
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortBy.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);  //Here Sort is class which is dynamically taking values here

      Page<Post> posts = postRepository.findAll(pageable);
       // it returns a Page object containing a subset of entities based on the pagination information.

        //get content for page object
        List<PostDto> content = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

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
        return mapToDto(post);

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
        PostDto updatedDto = mapToDto(updatedPost);
        return updatedDto;
    }

    @Override
    public void deletePostById(long id) {
        //First I need to retrieve entity by id just to check id is available or not
        postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + ": Id Not Found"));
        postRepository.deleteById(id); //or postRepository.delete(post)
    }

    //Using ModelMapper for converting PostDto into post Emtity
    private PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }
    //Using ModelMapper to Convert Post Entity into PostDto
    private Post maptoEntity(PostDto dto){
        Post post = modelMapper.map(dto, Post.class);
        return post;
    }

    //Convert Post into DTO
    /*public PostDto convertPost(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }*/

    //Convert PostDto into Post Entity
   /* public Post convertPostDto(PostDto postDto){
        Post post = new Post();
        //post.setId(postDto.getId());  no need because id is auto increment
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }*/
}
