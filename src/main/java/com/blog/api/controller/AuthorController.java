package com.blog.api.controller;

import com.blog.api.entity.Author;
import com.blog.api.exception.BadRequestException;
import com.blog.api.exception.NotFoundException;
import com.blog.api.exception.UnprocessableEntityException;
import com.blog.api.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog/v1/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/create/")
    public ResponseEntity createAuthor(@RequestParam String authorName){
        if (authorName == null || authorName.isBlank()) {
            throw new UnprocessableEntityException("Author name is required");
        }
        if (authorName.length() > 50) {
            throw new UnprocessableEntityException("Author name must not be more than 50 characters!");
        }

        Author author = authorService.saveAndReturnAuthor(authorName);
        return new ResponseEntity(author, HttpStatus.CREATED);

    }


    @PutMapping("/update/by/author-id/{authorRdbmsId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("authorRdbmsId") Long authorRdbmsId,@RequestParam String authorName) {

        if (authorRdbmsId < 1) {
            throw new BadRequestException("Invalid authorRdbmsId!");
        }

        if (authorRdbmsId == null) {
            throw new BadRequestException("Please provide a authorRdbmsId with the request!");
        }
        if (authorName == null || authorName.isBlank()) {
            throw new BadRequestException("No field present to update!");
        }

        if (authorName.length() > 50) {
            throw new UnprocessableEntityException("Author name must not be more than 50 characters!");
        }

        Author authorStore = authorService.updateAuthorByAuthorRdbmsId(authorRdbmsId, authorName);
        return new ResponseEntity<>(authorStore, HttpStatus.ACCEPTED);

    }

    @GetMapping("/find/by/author-id/{authorRdbmsId}")
    public ResponseEntity<Author> findAuthorByAuthorRdbmsId(@PathVariable("authorRdbmsId") Long authorRdbmsId) {

        if (authorRdbmsId < 1) {
            throw new BadRequestException("Invalid authorRdbmsId!");
        }

        if (authorRdbmsId == null) {
            throw new BadRequestException("Please provide a authorRdbmsId with the request!");
        }
        Author theAuthor = authorService.findAuthorByAuthorRdbmsId(authorRdbmsId)
                .orElseThrow(() -> new NotFoundException("No author found by this authorRdbmsId!"));

        return new ResponseEntity<>(theAuthor, HttpStatus.OK);
    }

    @DeleteMapping("/delete/by/author-id/{authorRdbmsId}")
    public ResponseEntity<Void> deleteAuthorByAuthorRdbmsId(@PathVariable("authorRdbmsId") Long authorRdbmsId) {

        if (authorRdbmsId < 1) {
            throw new BadRequestException("Invalid authorRdbmsId!");
        }

        if (authorRdbmsId == null) {
            throw new BadRequestException("Please provide a authorRdbmsId with the request!");
        }

        authorService.deleteByAuthorId(authorRdbmsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
