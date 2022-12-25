package com.blog.api.service;

import com.blog.api.dto.PostDto;
import com.blog.api.entity.*;
import com.blog.api.exception.InternalServerErrorException;
import com.blog.api.exception.NotFoundException;
import com.blog.api.exception.UnprocessableEntityException;
import com.blog.api.helper.EntityState;
import com.blog.api.helper.Helper;
import com.blog.api.repository.PostRepository;
import com.blog.api.resource.PostResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostTagService postTagService;

    @Override
    public PostResource saveAndReturnPost(PostDto postDto) {

        if (postRepository.postTitleExists(postDto.getPostTitle()).isPresent()) {
            throw new UnprocessableEntityException("Post title already exists!");
        }
        Author theAuthor = authorService.findAuthorByAuthorRdbmsId(postDto.getAuthorRdbmsId())
                .orElseThrow(()-> new NotFoundException("No author found by this authorRdbmsId!"));


        Category theCategory = categoryService.findCategoryByCategoryRdbmsId(postDto.getCategoryRdbmsId())
                .orElseThrow(()-> new NotFoundException("No category found by this categoryRdbmsId!"));


        Post thePost = new Post();
        thePost.setPostTitle(postDto.getPostTitle());
        thePost.setPostUrlName(postRepository.postUrlNameExists(Helper.toSlug(postDto.getPostTitle())).isPresent()  ? Helper.toSlug(postDto.getPostTitle()+ Helper.getUniqueString()) : Helper.toSlug(postDto.getPostTitle()));
        thePost.setPostDescription(postDto.getPostDescription());
        thePost.setAuthor(theAuthor);
        thePost.setCategory(theCategory);
        thePost.setPostState(EntityState.ACTIVE.toString());
        thePost.setCreatedAt(Helper.getCurrentTimestamp());
        thePost.setUpdatedAt(Helper.getCurrentTimestamp());

        try {
            postRepository.save(thePost);
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }

        Set<Integer> tagRdbmsIds = new HashSet<>();
        List<Integer> postTagRdbmsIds = postDto.getPostTagRdbmsIds();
        postTagRdbmsIds.forEach(postTagRdbmsId -> {
            tagRdbmsIds.add(postTagRdbmsId);
        });


        List<Tag> tags = tagService.findAllTagsByMultipleIds(tagRdbmsIds);

        if (tags == null || tags.isEmpty()){
            throw new UnprocessableEntityException("Tag not found by this tagRdbmsIds!");
        }

        Set<String> postTags = new HashSet<>();

        tagRdbmsIds.forEach(tagRdbmsId -> {
            tags.forEach(tag -> {
                postTags.add(tag.getTagName());
                PostTag thePostTag = new PostTag();
                thePostTag.setPost(thePost);
                thePostTag.setTag(tag);

                try {
                    postTagService.savePostTag(thePostTag.getPost(),tag);
                } catch (Exception e) {
                    throw new InternalServerErrorException("Something went wrong on the server!");
                }
            });
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


        return thePostResource;
    }

    @Override
    public PostResource updatePostByPostRdbmsId(Long postRdbmsId, PostDto postDto) {


        if (postRepository.postTitleExists(postDto.getPostTitle()).isPresent()) {
            throw new UnprocessableEntityException("Post title already exists!");
        }

        Post thePost = postRepository.findById(postRdbmsId)
                .orElseThrow(() -> new NotFoundException("No post found by this postRdbmsId!"));

        Author theAuthor = authorService.findAuthorByAuthorRdbmsId(postDto.getAuthorRdbmsId())
                .orElseThrow(() -> new NotFoundException("No author found by this authorRdbmsId!"));

        Category theCategory = categoryService.findCategoryByCategoryRdbmsId(postDto.getCategoryRdbmsId())
                .orElseThrow(() -> new NotFoundException("No category found by this categoryRdbmsId!"));


        if (postDto.getPostTitle() != null || !postDto.getPostTitle().isEmpty()) {
            thePost.setPostTitle(thePost.getPostTitle());
            thePost.setPostUrlName(postRepository.postUrlNameExists(Helper.toSlug(postDto.getPostTitle())).isPresent()  ? Helper.toSlug(postDto.getPostTitle()+ Helper.getUniqueString()) : Helper.toSlug(postDto.getPostTitle()));
        }

        if (postDto.getPostDescription() != null || !postDto.getPostDescription().isEmpty()) {
            thePost.setPostDescription(thePost.getPostDescription());
        }

        if (postDto.getAuthorRdbmsId() != null) {
            thePost.setAuthor(theAuthor);
        }

        if (postDto.getCategoryRdbmsId() != null) {
            thePost.setCategory(theCategory);
        }

        try {
            postRepository.save(thePost);
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }

        Set<String> postTags = new HashSet<>();;

        if (postDto.getPostTagRdbmsIds() != null || !postDto.getPostTagRdbmsIds().isEmpty()) {

            Set<Integer> tagRdbmsIds = new HashSet<>();
            List<Integer> postTagRdbmsIds = postDto.getPostTagRdbmsIds();
            postTagRdbmsIds.forEach(postTagRdbmsId -> {
                tagRdbmsIds.add(postTagRdbmsId);
            });


            List<Tag> tags = tagService.findAllTagsByMultipleIds(tagRdbmsIds);

            if (tags == null || tags.isEmpty()) {
                throw new UnprocessableEntityException("Tag not found by this tagRdbmsIds!");
            }

            tagRdbmsIds.forEach(tagRdbmsId -> {
                tags.forEach(tag -> {
                    postTags.add(tag.getTagName());
                    try {
                        postTagService.savePostTag(thePost,tag);
                    } catch (Exception e) {
                        throw new InternalServerErrorException("Something went wrong on the server!");
                    }
                });
            });
        }

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
        return thePostResource;
    }

    @Override
    public Page<Post> findAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Optional<Post> findPostByPostRdbmsId(Long postRdbmsId) {
        return postRepository.findById(postRdbmsId);
    }

    @Override
    public void deletePostByPostPostRdbmsId(Long postRdbmsId) {

        Post thePost = postRepository.findById(postRdbmsId)
                .orElseThrow(() -> new NotFoundException("No post found by this postRdbmsId!"));

        try {
            categoryService.deleteCategoryByCategoryRdbmsId(thePost.getCategory().getCategoryRdbmsId());
        } catch (Exception e) {
            throw new InternalServerErrorException("Something went wrong on the server!");
        }
    }
}
