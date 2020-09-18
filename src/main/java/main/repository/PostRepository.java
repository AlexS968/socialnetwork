package main.repository;

import main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByTitle(String name, Pageable pageable);

}
