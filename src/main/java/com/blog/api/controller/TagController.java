package com.blog.api.controller;

import com.blog.api.entity.Tag;
import com.blog.api.exception.BadRequestException;
import com.blog.api.exception.NotFoundException;
import com.blog.api.exception.UnprocessableEntityException;
import com.blog.api.helper.Helper;
import com.blog.api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/blog/v1/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    private static final int MAX_PAGE_SIZE = 20;

    @PostMapping("/create/")
    public ResponseEntity<Tag> createTag(@RequestParam String tagName) {

        if (tagName == null || tagName.isBlank()) {
            throw new BadRequestException("Tag name is required!");
        }

        if (tagName.length() > 50) {
            throw new BadRequestException("Tag name must not be greater than 50 characters!");
        }

        Tag createTag = tagService.saveAndReturnTag(tagName);
        return new ResponseEntity<>(createTag, HttpStatus.CREATED);

    }


    @PutMapping("/update/by/tag-id/{tagRdbmsId}")
    public ResponseEntity<Tag> updateTagByTagRdbmsId(@PathVariable("tagRdbmsId") Integer tagRdbmsId,
                                                     @RequestParam("tagName") String tagName) {


        if (tagRdbmsId < 1) {
            throw new BadRequestException("Invalid tagRdbmsId!");
        }

        if (tagName.length() > 50) {
            throw new UnprocessableEntityException("Tag name must not be more than 50 characters!");
        }

        Tag updateTag = tagService.updateTagByTagRdbmsId(tagRdbmsId, tagName);
        return new ResponseEntity<>(updateTag, HttpStatus.ACCEPTED);

    }

    @GetMapping("/find/all/")
    public ResponseEntity<?> findAllTags(@PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable) {

        Page<Tag> tags = tagService.findAllCategory(pageable);

        if (tags.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            long totalTag = tags.getTotalElements();
            int numberOfPageTag = tags.getNumberOfElements();

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Total-Count", String.valueOf(totalTag));

            if (numberOfPageTag < totalTag) {
                headers.add("first", Helper.buildPageUri(PageRequest.of(0, tags.getSize())));
                headers.add("last", Helper.buildPageUri(PageRequest.of(tags.getTotalPages() - 1, tags.getSize())));

                if (tags.hasNext()) {
                    headers.add("next", Helper.buildPageUri(tags.nextPageable()));
                }

                if (tags.hasPrevious()) {
                    headers.add("prev", Helper.buildPageUri(tags.previousPageable()));
                }

                return new ResponseEntity<>(tags.getContent(), headers, HttpStatus.PARTIAL_CONTENT);
            } else {
                return new ResponseEntity<>(tags.getContent(), headers, HttpStatus.OK);
            }
        }

    }

    @GetMapping("/find/by/tag-id/{tagRdbmsId}")
    public ResponseEntity<Tag> findTagByTagRdbmsId(@PathVariable("tagRdbmsId") Integer tagRdbmsId) {

        if (tagRdbmsId == null) {
            throw new BadRequestException("Please provide a tagRdbmsId with the request!");
        }

        if (tagRdbmsId < 1) {
            throw new BadRequestException("Invalid tagRdbmsId!");
        }

        Tag theTag= tagService.findTagByTagRdbmsId(tagRdbmsId)
                .orElseThrow(() -> new NotFoundException("No tag found by this tagRdbmsId!"));
        return new ResponseEntity<>(theTag, HttpStatus.OK);
    }

    @GetMapping("/find/by/tag-url-name/{tagUrlName}")
    public ResponseEntity<Tag> findTagByTagUrlName(@PathVariable("tagUrlName") String tagUrlName) {

        if (tagUrlName == null || tagUrlName.isBlank()) {
            throw new BadRequestException("Tag url name is required!");
        }

        Tag theTag = tagService.findTagByTagUrlName(tagUrlName)
                .orElseThrow(() -> new NotFoundException("No tag found by this tagUrlName!"));
        return new ResponseEntity<>(theTag, HttpStatus.OK);
    }


    @DeleteMapping("/delete/by/tag-id/{tagRdbmsId}")
    public ResponseEntity<Void> deleteTagByTagRdbmsId(@PathVariable("tagRdbmsId") Integer tagRdbmsId) {

        if (tagRdbmsId == null) {
            throw new BadRequestException("Please provide a tagRdbmsId with the request!");
        }

        if (tagRdbmsId < 1) {
            throw new BadRequestException("Invalid tagRdbmsId!");
        }

        tagService.deleteTagByTagRdbmsId(tagRdbmsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
