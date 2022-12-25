package com.blog.api.service;

import com.blog.api.entity.Post;
import com.blog.api.entity.PostTag;
import com.blog.api.entity.Tag;
import com.blog.api.helper.EntityState;
import com.blog.api.helper.Helper;
import com.blog.api.repository.PostTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PostTagServiceImpl implements PostTagService{

    @Autowired
    private PostTagRepository postTagRepository;


    @Override
    public void savePostTag(Post post, Tag tag) {
        PostTag thePostTag = new PostTag();
        thePostTag.setPost(post);
        thePostTag.setTag(tag);
        thePostTag.setPostState(EntityState.ACTIVE.toString());
        thePostTag.setCreatedAt(Helper.getCurrentTimestamp());
        thePostTag.setUpdatedAt(Helper.getCurrentTimestamp());
        postTagRepository.save(thePostTag);
    }


}
