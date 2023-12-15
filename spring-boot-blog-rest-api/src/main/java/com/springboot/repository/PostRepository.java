package com.springboot.repository;

import com.springboot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

//No need to annotate with any annotation because JpaRepository internally implements SimpleJpaRepository Class
//and SimpleRepositoryClass annotated with @Repository and @Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    //no more codes needed
    //it will provide all crud methods to talk with database
}
