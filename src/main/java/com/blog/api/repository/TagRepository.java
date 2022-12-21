package com.blog.api.repository;

import com.blog.api.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag,Integer> {

    @Query(value = "SELECT  * FROM tags WHERE tag_name = ?1", nativeQuery = true)
    Optional<Tag> tagNameExists(String tagName);

    @Query(value = "SELECT  * FROM tags WHERE tag_url_name = ?1", nativeQuery = true)
    Optional<Tag> tagUrlNameExists(String tagUrlName);

    @Query(value = "SELECT  * FROM tags WHERE tag_id IN(?1)", nativeQuery = true)
    List<Tag> findAllTagsByMultipleIds(Set<Integer> tagRdbmsIds);

    @Query(value = "SELECT  * FROM tags WHERE tag_url_name = ?1", nativeQuery = true)
    Optional<Tag> findTagByTagUrlName(String tagUrlName);
}
