package com.blog.api.service;

import com.blog.api.entity.Category;
import com.blog.api.entity.Tag;
import com.blog.api.exception.NotFoundException;
import com.blog.api.exception.UnprocessableEntityException;
import com.blog.api.helper.EntityState;
import com.blog.api.helper.Helper;
import com.blog.api.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag saveAndReturnTag(String tagName) {

        if (tagRepository.tagNameExists(tagName).isPresent()) {
            throw new UnprocessableEntityException("Tag name already exists!");
        }
        Tag theTag = new Tag();
        theTag.setTagName(tagName);
        theTag.setTagUrlName(tagRepository.tagUrlNameExists(Helper.toSlug(tagName)).isPresent()  ? Helper.toSlug(tagName+ Helper.getUniqueString()) : Helper.toSlug(tagName));
        theTag.setTagState(EntityState.ACTIVE.toString());
        theTag.setCreatedAt(Helper.getCurrentTimestamp());
        theTag.setUpdatedAt(Helper.getCurrentTimestamp());
        tagRepository.save(theTag);

        return theTag;
    }

    @Override
    public List<Tag> findAllTagsByMultipleIds(Set<Integer> tagRdbmsIds) {
        return tagRepository.findAllTagsByMultipleIds(tagRdbmsIds);
    }

    @Override
    public Tag updateTagByTagRdbmsId(Integer tagRdbmsId, String tagName) {


        Tag theTag = tagRepository.findById(tagRdbmsId)
                .orElseThrow(()-> new NotFoundException("No tag found by this tagRdbmsId!"));

        if (theTag.getTagName().equals(tagName)){
            throw new UnprocessableEntityException("Tag name already exists!");
        }
        if (tagName != null && !tagName.isBlank()) {
            theTag.setTagName(tagName);
            theTag.setTagUrlName(tagRepository.tagUrlNameExists(Helper.toSlug(tagName)).isPresent()  ? Helper.toSlug(tagName+ Helper.getUniqueString()) : Helper.toSlug(tagName));
        }

        tagRepository.save(theTag);

        return theTag;
    }

    @Override
    public Page<Tag> findAllCategory(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Optional<Tag> findTagByTagRdbmsId(Integer tagRdbmsId) {
        return tagRepository.findById(tagRdbmsId);

    }

    @Override
    public Optional<Tag> findTagByTagUrlName(String tagUrlName) {
        Tag theTag = tagRepository.findTagByTagUrlName(tagUrlName)
                .orElseThrow(() -> new NotFoundException("No tag found by this tagUrlName!"));

        return tagRepository.findTagByTagUrlName(tagUrlName);
    }

    @Override
    public void deleteTagByTagRdbmsId(Integer tagRdbmsId) {
        Tag theTag = tagRepository.findById(tagRdbmsId)
                .orElseThrow(() -> new NotFoundException("No tag found by this tagRdbmsId!"));

        tagRepository.deleteById(theTag.getTagRdbmsId());
    }
}
