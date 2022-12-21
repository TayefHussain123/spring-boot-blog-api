package com.blog.api.entity;

import com.sun.istack.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "authors")
@Validated
public class Author {
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorRdbmsId;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name = "author_name", unique = true)
    private String authorName;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 70)
    @Column(name = "author_url_name", unique = true)
    private String authorUrlName;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 10)
    @Column(name = "author_state")
    private String authorState;


    @Nullable
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Nullable
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Author() {
    }

    public Author(Long authorRdbmsId, String authorName, String authorUrlName, String authorState, @Nullable Timestamp createdAt, @Nullable Timestamp updatedAt) {
        this.authorRdbmsId = authorRdbmsId;
        this.authorName = authorName;
        this.authorUrlName = authorUrlName;
        this.authorState = authorState;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getAuthorRdbmsId() {
        return authorRdbmsId;
    }

    public void setAuthorRdbmsId(Long authorRdbmsId) {
        this.authorRdbmsId = authorRdbmsId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorUrlName() {
        return authorUrlName;
    }

    public void setAuthorUrlName(String authorUrlName) {
        this.authorUrlName = authorUrlName;
    }

    public String getAuthorState() {
        return authorState;
    }

    public void setAuthorState(String authorState) {
        this.authorState = authorState;
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
        Author author = (Author) o;
        return Objects.equals(authorRdbmsId, author.authorRdbmsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorRdbmsId);
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorRdbmsId=" + authorRdbmsId +
                ", authorName='" + authorName + '\'' +
                ", authorUrlName='" + authorUrlName + '\'' +
                ", authorState='" + authorState + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}