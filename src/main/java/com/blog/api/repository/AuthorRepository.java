package com.blog.api.repository;

import com.blog.api.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    @Query(value = "SELECT * FROM authors WHERE author_name=?1" , nativeQuery = true)
    Optional<Author> authorNameExists(String categoryName);

    @Query(value = "SELECT * FROM authors WHERE author_url_name=?1" , nativeQuery = true)
    Optional<Author> authorUrlNameExists(String authorUrlName);

}
