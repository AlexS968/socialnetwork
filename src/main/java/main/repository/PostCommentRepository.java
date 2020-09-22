package main.repository;

import main.model.PostComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {
    @Query( value = "SELECT * FROM post_comment WHERE post_id IN (:list)",
            nativeQuery = true)
    List<PostComment> getCommentsByList(Set<Integer> list);
}
