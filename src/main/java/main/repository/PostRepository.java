package main.repository;

import main.model.PersonAndPostView;
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

    // все посты этого пользователя
    // для каждого поста найти все лайки -> count + кометы + теги + файлы


    @Query(nativeQuery = true, value =

        "SELECT Person.*, Post.*, count(Post_like.id) as likes, Post_comment.*, Post2tag.*, Post_file.* FROM Post "
        + "JOIN Person ON Post.author_id = Person.id"
        + "JOIN Post_like ON Post.id = Post_like.post_id "
        + "JOIN Post_comment ON Post.id = Post_comment.post_id "
        + "JOIN Post2tag ON Post.id = Post2tag.post_id "
        + "JOIN Post_file ON Post.id = Post_file.post_id "
        + "WHERE Post.author_id = :authorId")
    Page<PersonAndPostView> getPostsByAuthorId(@Param("authorId") Integer authorId,
        Pageable pageable);

}
