package com.blog.api.service;

import com.blog.api.dto.PostDto;
import com.blog.api.entity.Post;
import com.blog.api.resource.PostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostService {
    PostResource saveAndReturnPost(PostDto postDto);

    PostResource updatePostByPostRdbmsId(Long postRdbmsId, PostDto postDto);

    Page<Post> findAllPosts(Pageable pageable);

    Optional<Post> findPostByPostRdbmsId(Long postRdbmsId);

    void deletePostByPostPostRdbmsId(Long postRdbmsId);
}
