package main.repository;

import main.model.Person;
import main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByTitle(String name, Pageable pageable);
    Page<Post> findByAuthor(Person person, Pageable pageable);

}
