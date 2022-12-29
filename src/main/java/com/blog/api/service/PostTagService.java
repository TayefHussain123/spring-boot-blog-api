package com.blog.api.service;

import com.blog.api.entity.Post;
import com.blog.api.entity.Tag;

public interface PostTagService {
    void savePostTag(Post post, Tag tag);
}
