package com.blog.api.repository;

import com.blog.api.entity.Category;
import com.blog.api.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface PostTagRepository extends JpaRepository<PostTag,Long> {

    @Query(value = "SELECT * FROM post_tags WHERE post_id=?1 AND tag_id IN(?2)" , nativeQuery = true)
    void deletePostTagByMultiplePostTagRdbmsIds(Long postRdbmsId, Set<Integer> tagRdbmsIds);
}
