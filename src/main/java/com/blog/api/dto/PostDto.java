package com.blog.api.dto;

import com.blog.api.entity.Tag;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PostDto {
    private Long postRdbmsId;
    private String postTitle;
    private String postDescription;
    private Long authorRdbmsId;
    private Integer categoryRdbmsId;
    private String postState;
    private List<Integer> postTagRdbmsIds;

    public PostDto() {
    }

    public PostDto(String postTitle, String postDescription, Long authorRdbmsId, Integer categoryRdbmsId, String postState, List<Integer> postTagRdbmsIds) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.authorRdbmsId = authorRdbmsId;
        this.categoryRdbmsId = categoryRdbmsId;
        this.postState = postState;
        this.postTagRdbmsIds = postTagRdbmsIds;
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

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public Long getAuthorRdbmsId() {
        return authorRdbmsId;
    }

    public void setAuthorRdbmsId(Long authorRdbmsId) {
        this.authorRdbmsId = authorRdbmsId;
    }

    public Integer getCategoryRdbmsId() {
        return categoryRdbmsId;
    }

    public void setCategoryRdbmsId(Integer categoryRdbmsId) {
        this.categoryRdbmsId = categoryRdbmsId;
    }

    public String getPostState() {
        return postState;
    }

    public void setPostState(String postState) {
        this.postState = postState;
    }

    public List<Integer> getPostTagRdbmsIds() {
        return postTagRdbmsIds;
    }

    public void setPostTagRdbmsIds(List<Integer> postTagRdbmsIds) {
        this.postTagRdbmsIds = postTagRdbmsIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDto postDto = (PostDto) o;
        return Objects.equals(postRdbmsId, postDto.postRdbmsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postRdbmsId);
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "postRdbmsId=" + postRdbmsId +
                ", postTitle='" + postTitle + '\'' +
                ", postDescription='" + postDescription + '\'' +
                ", authorRdbmsId=" + authorRdbmsId +
                ", categoryRdbmsId=" + categoryRdbmsId +
                ", postState='" + postState + '\'' +
                ", postTagRdbmsIds=" + postTagRdbmsIds +
                '}';
    }
}
