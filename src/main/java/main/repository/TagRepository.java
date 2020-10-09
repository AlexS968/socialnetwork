package main.repository;

import main.model.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {
    Optional<Tag> findTagByTag(String name);
    Iterable<Tag> findByTagLike(String tag);
}
