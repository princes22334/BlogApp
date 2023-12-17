package com.springboot.service;

import com.springboot.entity.Comment;
import com.springboot.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto dto);
    List<CommentDto> getCommentsByPostId(long postId);
}
