package main.repository;

import java.util.Date;
import java.util.Set;
import main.model.Person;
import main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

  Page<Post> findAll(Pageable pageable);

  Page<Post> findByTitle(String name, Pageable pageable);

  Page<Post> findByAuthor(Person person, Pageable pageable);

  @Query(nativeQuery = true, value =
      "SELECT * FROM post "
          + "JOIN person ON person.id = post.author_id "
          + "JOIN post2tag ON post2tag.post_id = post.id "
          + "WHERE "
          + "(:text is null or post_text LIKE :text) and "
          + "(:dateFrom is null or time >= :dateFrom) and "
          + "(:dateTo is null or time <= :dateTo) and "
          + "(COALESCE(:authorId) is null or (author_id IN (:authorId))) and"
          + "(COALESCE(:tagId) is null or (post2tag.tag_id IN (:tagId))) GROUP BY post.id")
  Page<Post> findByTextPeriodAuthor(
      @Param("text") String text,
      @Param("dateFrom") Date dateFrom,
      @Param("dateTo") Date dateTo,
      @Param("authorId") Set<Integer> authorId,
      @Param("tagId") Set<Integer> tags,
      Pageable pagable
  );

}
