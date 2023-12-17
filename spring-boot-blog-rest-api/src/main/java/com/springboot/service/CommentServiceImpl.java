package com.springboot.service;

import com.springboot.entity.Comment;
import com.springboot.entity.Post;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.payload.CommentDto;
import com.springboot.repository.CommentRepository;
import com.springboot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto dto) {
        Comment comment = convertCommentDto(dto);
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found: " + postId));

        //Set Post to comment entity
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        return convertComment(savedComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //retrieve comment by post id
        List<Comment> comments = commentRepository.findByPostId(postId);

        //to convert comment into comment dto
        List<CommentDto> commentsDto = comments.stream().map(comment -> convertComment(comment)).collect(Collectors.toList());
        return commentsDto;
    }

    public Comment convertCommentDto(CommentDto dto) {
        Comment comment = new Comment();
        comment.setName(dto.getName());
        comment.setEmail(dto.getEmail());
        comment.setBody(dto.getBody());
        return comment;
    }

    public CommentDto convertComment(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }


}
