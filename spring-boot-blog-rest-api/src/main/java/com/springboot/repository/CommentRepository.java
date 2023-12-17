package com.springboot.repository;

import com.springboot.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //JpaRepository interface implements SimpleJpaRepository class and internally annoted with
    //@Repository and @Transactional so no need to add @Repository

    //custom query method
    List<Comment> findByPostId(long postId);
}
