package com.blog.api.repository;

import com.blog.api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query(value = "SELECT * FROM posts WHERE post_title=?1" , nativeQuery = true)
    Optional<Post> postTitleExists(String postTitle);

    @Query(value = "SELECT * FROM posts WHERE post_url_name=?1" , nativeQuery = true)
    Optional<Post> postUrlNameExists(String postUrlName);
}
