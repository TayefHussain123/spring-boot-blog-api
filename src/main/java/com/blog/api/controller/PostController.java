package com.blog.api.controller;

import com.blog.api.dto.PostDto;
import com.blog.api.entity.Post;
import com.blog.api.exception.BadRequestException;
import com.blog.api.exception.InternalServerErrorException;
import com.blog.api.exception.NotFoundException;
import com.blog.api.helper.Helper;
import com.blog.api.resource.PostResource;
import com.blog.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/blog/v1/post")

public class PostController {
    @Autowired
    private PostService postService;


    private static final int MAX_PAGE_SIZE = 20;
    private Post post;

    @PostMapping("/create/")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {

        if (postDto.getAuthorRdbmsId() < 1) {
            throw new BadRequestException("Invalid authorRdbmsId!");
        }

        if (postDto.getAuthorRdbmsId() == null) {
            throw new BadRequestException("Post author filed is required!");
        }

        if (postDto.getCategoryRdbmsId() < 1) {
            throw new BadRequestException("Invalid categoryRdbmsId!");
        }


        if (postDto.getCategoryRdbmsId() == null) {
            throw new BadRequestException("Post category filed is required!");
        }


        if (postDto.getPostTagRdbmsIds().size() < 1) {
            throw new BadRequestException("Invalid tagRdbmsIds!");
        }

        if (postDto.getPostTagRdbmsIds().isEmpty() || postDto.getPostTagRdbmsIds() == null) {
            throw new BadRequestException("Post tag filed is required!");
        }

        if (postDto.getPostTitle() == null || postDto.getPostTitle().isBlank()) {
            throw new BadRequestException("Post title field is required!");
        }

        if (postDto.getPostTitle().length() > 200) {
            throw new BadRequestException("Post title must not be more than 200 characters!");
        }
        if (postDto.getPostDescription() == null && postDto.getPostDescription().isBlank()) {
            throw new BadRequestException("Post description field is required!");

        }
        if (postDto.getPostDescription().length() > 1500) {
            throw new BadRequestException("Post description must not be more than 1500 characters!");
        }



        try {
            PostResource postResource = postService.saveAndReturnPost(postDto);
            return new ResponseEntity<>(postResource, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }
    }


    @PutMapping("/update/by/post-id/{postRdbmsId}")
    public ResponseEntity <PostResource> updatePostByPostRdbmsId(@PathVariable("postRdbmsId") Long postRdbmsId, @RequestBody PostDto postDto) {


        if (postDto.getPostTitle().length() > 200) {
            throw new BadRequestException("Post title must not be more than 200 characters!");
        }

        if (postDto.getPostDescription().length() > 1500) {
            throw new BadRequestException("Post description must not be more than 1500 characters!");
        }


        if (postDto.getAuthorRdbmsId() < 1) {
            throw new BadRequestException("Invalid authorRdbmsId!");
        }

        if (postDto.getCategoryRdbmsId() < 1) {
            throw new BadRequestException("Invalid categoryRdbmsId!");
        }

        try {
            PostResource createPost = postService.updatePostByPostRdbmsId(postRdbmsId,postDto);
            return new ResponseEntity<>(createPost, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on server!");
        }
    }


    @RequestMapping("/find/all/")
    public ResponseEntity findAllPosts(@PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable) {

        Page<Post> posts = postService.findAllPosts(pageable);

        List<PostResource> postResources = new ArrayList<>();


        Set<String> postTags = new HashSet<>();
        posts.forEach(post -> {

            post.getTags().forEach(tag -> {
                postTags.add(tag.getTagName());
            });

            postResources.add(new PostResource(post.getPostRdbmsId(), post.getPostTitle(), post.getPostUrlName(), post.getPostDescription(),
                    post.getAuthor().getAuthorName(), post.getCategory().getCategoryName(), postTags, post.getPostState(), post.getCreatedAt(), post.getUpdatedAt()));
        });


        if (postResources.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            long totalPosts = posts.getTotalElements();
            int numberOfPagePosts = posts.getNumberOfElements();

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Total-Count", String.valueOf(totalPosts));

            if (numberOfPagePosts < totalPosts) {
                headers.add("first", Helper.buildPageUri(PageRequest.of(0, posts.getSize())));
                headers.add("last", Helper.buildPageUri(PageRequest.of(posts.getTotalPages() - 1, posts.getSize())));

                if (posts.hasNext()) {
                    headers.add("next", Helper.buildPageUri(posts.nextPageable()));
                }

                if (posts.hasPrevious()) {
                    headers.add("prev", Helper.buildPageUri(posts.previousPageable()));
                }

                return new ResponseEntity<>(posts.getContent(), headers, HttpStatus.PARTIAL_CONTENT);
            } else {
                return new ResponseEntity<>(posts.getContent(), headers, HttpStatus.OK);
            }
        }
    }
        @GetMapping("/find/by/post-id/{postRdbmsId}")
        public ResponseEntity<PostResource> findPostByPostId (@PathVariable("postRdbmsId") Long postRdbmsId){

            Post thePost = postService.findPostByPostRdbmsId(postRdbmsId)
                    .orElseThrow(() -> new NotFoundException("No post found by this postRdbmsId!"));

            Set<String> postTags = new HashSet<>();

            thePost.getTags().forEach(tag -> {
                postTags.add(tag.getTagName());
            });


            PostResource thePostResource = new PostResource(
                    thePost.getPostRdbmsId(),
                    thePost.getPostTitle(),
                    thePost.getPostUrlName(),
                    thePost.getPostDescription(),
                    thePost.getAuthor().getAuthorName(),
                    thePost.getCategory().getCategoryName(),
                    postTags,
                    thePost.getPostState(),
                    thePost.getCreatedAt(),
                    thePost.getUpdatedAt()
            );
            return new ResponseEntity<>(thePostResource, HttpStatus.OK);
        }



    @DeleteMapping("/delete/by/post-id/{postRdbmsId}")
    public ResponseEntity <Void> deletePostByPostRdbmsId(@PathVariable("postRdbmsId") Long postRdbmsId) {
        if (postRdbmsId == null) {
            throw new BadRequestException("Please provide a postId with the request!");
        }
        try {
            postService.deletePostByPostPostRdbmsId(postRdbmsId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {

            throw new InternalServerErrorException("Something wrong on server!");
        }

    }






}