package main.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;
import main.model.Person;
import main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

  Page<Post> findAll(Pageable pageable);

  Page<Post> findByTitle(String name, Pageable pageable);

  Page<Post> findByAuthor(Person person, Pageable pageable);

  List<Post> findByAuthor(Person person);

  @Modifying
  @Transactional
  @Query(nativeQuery = true, value = "delete from post where (author_id = :authorId and id > 0)")
  void deleteByAuthorId( @Param("authorId")Integer authorId);

  @Query(nativeQuery = true, value =
      "SELECT DISTINCT po.* FROM post po "
          + "INNER JOIN person pe  ON pe.id = po.author_id "
          + "INNER JOIN post2tag pt ON pt.post_id = po.id "
          + "WHERE "
          + "((:text is null) OR (po.post_text LIKE :text) OR (po.title LIKE :text)) AND "
          + "((:dateFrom is null AND :dateTo is null) or (po.time >= :dateFrom AND po.time <= :dateTo)) AND  "
          + "(COALESCE(:authorId) is null or (po.author_id IN (:authorId))) AND "
          + "(COALESCE(:tagId) is null or (pt.tag_id IN (:tagId)))")
  Page<Post> findByTextPeriodAuthor(
      @Param("text") String text,
      @Param("dateFrom") Date dateFrom,
      @Param("dateTo") Date dateTo,
      @Param("authorId") Set<Integer> authorId,
      @Param("tagId") Set<Integer> tags,
      Pageable pagable
  );

}
