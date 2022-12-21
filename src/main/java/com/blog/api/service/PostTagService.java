package com.blog.api.service;

import com.blog.api.entity.Post;
import com.blog.api.entity.PostTag;
import com.blog.api.entity.Tag;

import java.util.Set;

public interface PostTagService {

    void savePostTag(Post post, Tag tag);

}
