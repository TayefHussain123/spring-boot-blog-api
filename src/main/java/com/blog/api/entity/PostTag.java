package com.blog.api.entity;

import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "post_tags")
@Validated
public class PostTag {
    @Id
    @Column(name = "post_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postTagRdbmsId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @NotBlank
    @Size(min = 3, max = 10)
    @Column(name = "post_state")
    private String postState;

    @Nullable
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Nullable
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public PostTag() {
    }

    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }

    public Long getPostTagRdbmsId() {
        return postTagRdbmsId;
    }

    public void setPostTagRdbmsId(Long postTagRdbmsId) {
        this.postTagRdbmsId = postTagRdbmsId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getPostState() {
        return postState;
    }

    public void setPostState(String postState) {
        this.postState = postState;
    }

    @Nullable
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@Nullable Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Nullable
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@Nullable Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostTag postTag = (PostTag) o;
        return Objects.equals(postTagRdbmsId, postTag.postTagRdbmsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postTagRdbmsId);
    }

    @Override
    public String toString() {
        return "PostTag{" +
                "postTagRdbmsId=" + postTagRdbmsId +
                ", post=" + post +
                ", tag=" + tag +
                ", postState='" + postState + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
