package main.service;

import main.model.Post;
import main.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository){
        this.repository = repository;
    }

    public Page<Post> getPostsByTitle(String name, Pageable pageable){
        return repository.findByTitle(name, pageable);
    }

    public Page<Post> getAllPosts(Pageable pageable){
        return repository.findAll(pageable);
    }
}
