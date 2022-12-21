package com.blog.api.entity;

import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "posts")
@Validated
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postRdbmsId;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 200)
    @Column(name = "post_title", unique = true)
    private String postTitle;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 250)
    @Column(name = "post_url_name", unique = true)
    private String postUrlName;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 1500)
    @Column(name = "post_description")
    private String postDescription;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "post_tags",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")
            })

    private Set<Tag> tags;

    @NotNull
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

    public Post() {
    }

    public Post(Long postRdbmsId, String postTitle, String postUrlName, String postDescription, Author author, Category category, Set<Tag> tags, String postState, @Nullable Timestamp createdAt, @Nullable Timestamp updatedAt) {
        this.postRdbmsId = postRdbmsId;
        this.postTitle = postTitle;
        this.postUrlName = postUrlName;
        this.postDescription = postDescription;
        this.author = author;
        this.category = category;
        this.tags = tags;
        this.postState = postState;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getPostUrlName() {
        return postUrlName;
    }

    public void setPostUrlName(String postUrlName) {
        this.postUrlName = postUrlName;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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
        Post post = (Post) o;
        return Objects.equals(postRdbmsId, post.postRdbmsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postRdbmsId);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postRdbmsId=" + postRdbmsId +
                ", postTitle='" + postTitle + '\'' +
                ", postUrlName='" + postUrlName + '\'' +
                ", postDescription='" + postDescription + '\'' +
                ", author=" + author +
                ", category=" + category +
                ", tags=" + tags +
                ", postState='" + postState + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}