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
@Table(name = "categories")
@Validated
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryRdbmsId;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "category_name", unique = true)
    private String categoryName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 70)
    @Column(name = "category_url_name", unique = true)
    private String categoryUrlName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 10)
    @Column(name = "category_state")
    private String categoryState;

    @Nullable
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Nullable
    @Column(name = "updated_at")
    private Timestamp updatedAt;


    public Category() {
    }

    public Category(Integer categoryRdbmsId, String categoryName, String categoryUrlName, String categoryState, @Nullable Timestamp createdAt, @Nullable Timestamp updatedAt) {
        this.categoryRdbmsId = categoryRdbmsId;
        this.categoryName = categoryName;
        this.categoryUrlName = categoryUrlName;
        this.categoryState = categoryState;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getCategoryRdbmsId() {
        return categoryRdbmsId;
    }

    public void setCategoryRdbmsId(Integer categoryRdbmsId) {
        this.categoryRdbmsId = categoryRdbmsId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryUrlName() {
        return categoryUrlName;
    }

    public void setCategoryUrlName(String categoryUrlName) {
        this.categoryUrlName = categoryUrlName;
    }

    public String getCategoryState() {
        return categoryState;
    }

    public void setCategoryState(String categoryState) {
        this.categoryState = categoryState;
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
        Category category = (Category) o;
        return categoryRdbmsId == category.categoryRdbmsId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryRdbmsId);
    }


    @Override
    public String toString() {
        return "Category{" +
                "categoryRdbmsId=" + categoryRdbmsId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryUrlName='" + categoryUrlName + '\'' +
                ", categoryState='" + categoryState + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}