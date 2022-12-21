package com.blog.api.service;

import com.blog.api.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagService {
    Tag saveAndReturnTag(String tagName);

    List<Tag> findAllTagsByMultipleIds(Set<Integer> tagRdbmsIds);

    Tag updateTagByTagRdbmsId(Integer tagRdbmsId, String tagName);

    Page<Tag> findAllCategory(Pageable pageable);

    Optional<Tag> findTagByTagRdbmsId(Integer tagRdbmsId);

    Optional<Tag> findTagByTagUrlName(String tagUrlName);

    void deleteTagByTagRdbmsId(Integer tagRdbmsId);
}
