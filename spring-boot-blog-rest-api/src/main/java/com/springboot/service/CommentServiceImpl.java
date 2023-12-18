package com.springboot.service;

import com.springboot.entity.Comment;
import com.springboot.entity.Post;
import com.springboot.exception.BlogApiException;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.payload.CommentDto;
import com.springboot.repository.CommentRepository;
import com.springboot.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    //Constructor based dependencies injection
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto dto) {
        Comment comment = mapToEntity(dto);
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found: " + postId));

        //Set Post to comment entity
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        //retrieve comment by post id
        List<Comment> comments = commentRepository.findByPostId(postId);

        //to convert comment into comment dto
        List<CommentDto> commentsDto = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentsDto;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        //retrieve post by post id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Available"));

        //retrieve comment by commentId
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new ResourceNotFoundException("Comment not Found"));

        //Will check this comment belongs to particular post or not
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
        //first check for post
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post id Not available"));

        //check for comment
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not Found"));

        //  validate if comment belongs to particular post or not
        //  if(comment.getPost().getId().equals(post.getId())){
        //  throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to Post");

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
    }

    @Override
    public void deleteComment(Long postId, Long CommentId) {
        //first retrieve the post from postId

    }

    //Using ModelMapper Library to Convert comment Entity to Dto
    private CommentDto mapToDto(Comment comment) {
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }

    private Comment mapToEntity(CommentDto dto) {
        Comment comment = modelMapper.map(dto, Comment.class);
        return comment;
    }


    /*public Comment mapToEntity(CommentDto dto) {
        Comment comment = new Comment();
        comment.setName(dto.getName());
        comment.setEmail(dto.getEmail());
        comment.setBody(dto.getBody());
        return comment;
    }

    public CommentDto maoToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }*/


}
