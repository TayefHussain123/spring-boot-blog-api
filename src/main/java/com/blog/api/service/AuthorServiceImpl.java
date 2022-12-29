package com.blog.api.service;

import com.blog.api.entity.Author;
import com.blog.api.exception.InternalServerErrorException;
import com.blog.api.exception.NotFoundException;
import com.blog.api.exception.UnprocessableEntityException;
import com.blog.api.helper.EntityState;
import com.blog.api.helper.Helper;
import com.blog.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author saveAndReturnAuthor(String authorName) {


        if (authorRepository.authorNameExists(authorName).isPresent()) {
            throw new UnprocessableEntityException("Author name already exists!");
        }

        Author theAuthor = new Author();
        theAuthor.setAuthorName(authorName);
        theAuthor.setAuthorUrlName(authorRepository.authorNameExists(Helper.toSlug(authorName)).isPresent()  ? Helper.toSlug(authorName+ Helper.getUniqueString()) : Helper.toSlug(authorName));
        theAuthor.setAuthorState(EntityState.ACTIVE.toString());
        theAuthor.setCreatedAt(Helper.getCurrentTimestamp());
        theAuthor.setUpdatedAt(Helper.getCurrentTimestamp());

        try {
            return authorRepository.save(theAuthor);
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }

    }

    @Override
    public Optional<Author> findAuthorByAuthorRdbmsId(Long authorRdbmsId) {
        return authorRepository.findById(authorRdbmsId);

    }

    @Override
    public Author updateAuthorByAuthorRdbmsId(Long authorRdbmsId,String authorName) {


        if (authorRepository.authorNameExists(authorName).isPresent()) {
            throw new UnprocessableEntityException("Author name already exists!");
        }


        Author theAuthor= authorRepository.findById(authorRdbmsId)
                .orElseThrow(()-> new NotFoundException("No author found by this authorRdbmsId!"));

        if (theAuthor.getAuthorName().equals(authorName)){
            throw new UnprocessableEntityException("Author name already exists!");
        }
        if (authorName != null || !authorName.isBlank()) {
            theAuthor.setAuthorName(authorName);
            theAuthor.setAuthorUrlName(authorRepository.authorNameExists(Helper.toSlug(authorName)).isPresent()  ? Helper.toSlug(authorName+ Helper.getUniqueString()) : Helper.toSlug(authorName));
        }

        try {
            return authorRepository.save(theAuthor);
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }



    }

    @Override
    public void deleteByAuthorId(Long authorRdbmsId) {

        Author theAuthor = authorRepository.findById(authorRdbmsId)
                .orElseThrow(() -> new NotFoundException("No author found by this authorRdbmsId!"));
        try {
            authorRepository.deleteById(theAuthor.getAuthorRdbmsId());
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }
    }
}
