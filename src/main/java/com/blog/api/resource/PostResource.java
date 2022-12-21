package com.blog.api.resource;

import com.blog.api.entity.Tag;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

public class PostResource {
    private Long postRdbmsId;
    private String postTitle;
    private String postTitleUrlName;
    private String postDescription;
    private String postAuthorName;
    private String postCategory;
    private Set<String> postTags;
    private String postState;
    private Timestamp postCratedAt;
    private Timestamp postUpdatedAt;

    public PostResource() {
    }

    public PostResource(Long postRdbmsId, String postTitle, String postTitleUrlName, String postDescription, String postAuthorName, String postCategory, Set<String> postTags, String postState, Timestamp postCratedAt, Timestamp postUpdatedAt) {
        this.postRdbmsId = postRdbmsId;
        this.postTitle = postTitle;
        this.postTitleUrlName = postTitleUrlName;
        this.postDescription = postDescription;
        this.postAuthorName = postAuthorName;
        this.postCategory = postCategory;
        this.postTags = postTags;
        this.postState = postState;
        this.postCratedAt = postCratedAt;
        this.postUpdatedAt = postUpdatedAt;
    }

    public Long getPostRdbmsId() {
        return postRdbmsId;
    }

    public void setPostRdbmsId(Long postRdbmsId) {
        this.postRdbmsId = postRdbmsId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostTitleUrlName() {
        return postTitleUrlName;
    }

    public void setPostTitleUrlName(String postTitleUrlName) {
        this.postTitleUrlName = postTitleUrlName;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostAuthorName() {
        return postAuthorName;
    }

    public void setPostAuthorName(String postAuthorName) {
        this.postAuthorName = postAuthorName;
    }

    public String getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(String postCategory) {
        this.postCategory = postCategory;
    }

    public Set<String> getPostTags() {
        return postTags;
    }

    public void setPostTags(Set<String> postTags) {
        this.postTags = postTags;
    }

    public String getPostState() {
        return postState;
    }

    public void setPostState(String postState) {
        this.postState = postState;
    }

    public Timestamp getPostCratedAt() {
        return postCratedAt;
    }

    public void setPostCratedAt(Timestamp postCratedAt) {
        this.postCratedAt = postCratedAt;
    }

    public Timestamp getPostUpdatedAt() {
        return postUpdatedAt;
    }

    public void setPostUpdatedAt(Timestamp postUpdatedAt) {
        this.postUpdatedAt = postUpdatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostResource that = (PostResource) o;
        return Objects.equals(postTitle, that.postTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postTitle);
    }

    @Override
    public String toString() {
        return "PostResource{" +
                "postRdbmsId='" + postRdbmsId + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", postTitleUrlName='" + postTitleUrlName + '\'' +
                ", postDescription='" + postDescription + '\'' +
                ", postAuthorName='" + postAuthorName + '\'' +
                ", postCategory='" + postCategory + '\'' +
                ", postTags=" + postTags +
                ", postState='" + postState + '\'' +
                ", postCratedAt=" + postCratedAt +
                ", postUpdatedAt=" + postUpdatedAt +
                '}';
    }
}
