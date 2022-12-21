package com.blog.api.service;

import com.blog.api.entity.Author;

import java.util.Optional;

public interface AuthorService {
    Author saveAndReturnAuthor(String authorName);

    Optional<Author> findAuthorByAuthorRdbmsId(Long authorRdbmsId);

    Author updateAuthorByAuthorRdbmsId(Long authorRdbmsId,String authorName);

    void deleteByAuthorId(Long authorRdbmsId);
}
