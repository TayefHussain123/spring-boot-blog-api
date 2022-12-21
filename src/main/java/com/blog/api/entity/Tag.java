package com.blog.api.entity;

import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Table(name = "tags")
@Validated
public class Tag {
    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagRdbmsId;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "tag_name", unique = true)
    private String tagName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 70)
    @Column(name = "tag_url_name", unique = true)
    private String tagUrlName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 10)
    @Column(name = "tag_state")
    private String tagState;

    @Nullable
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Nullable
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Tag() {
    }

    public Tag(Integer tagRdbmsId, String tagName, String tagUrlName, String tagState, @Nullable Timestamp createdAt, @Nullable Timestamp updatedAt) {
        this.tagRdbmsId = tagRdbmsId;
        this.tagName = tagName;
        this.tagUrlName = tagUrlName;
        this.tagState = tagState;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getTagRdbmsId() {
        return tagRdbmsId;
    }

    public void setTagRdbmsId(Integer tagRdbmsId) {
        this.tagRdbmsId = tagRdbmsId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagUrlName() {
        return tagUrlName;
    }

    public void setTagUrlName(String tagUrlName) {
        this.tagUrlName = tagUrlName;
    }

    public String getTagState() {
        return tagState;
    }

    public void setTagState(String tagState) {
        this.tagState = tagState;
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
        Tag tag = (Tag) o;
        return Objects.equals(tagRdbmsId, tag.tagRdbmsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagRdbmsId);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagRdbmsId=" + tagRdbmsId +
                ", tagName='" + tagName + '\'' +
                ", tagUrlName='" + tagUrlName + '\'' +
                ", tagState='" + tagState + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}