package main.service;

import main.data.response.FeedsResponse;
import main.model.Post;
import main.model.PostComment;
import main.repository.PostCommentRepository;
import main.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;
    private final PostCommentRepository commentRepository;

    public PostService(PostRepository repository, PostCommentRepository commentRepository){
        this.repository = repository;
        this.commentRepository = commentRepository;
    }

//    public Page<Post> getPostsByTitle(String name, Pageable pageable){
//        return repository.findByTitle(name, pageable);
//    }

//    public Page<Post> getAllPosts(int offset, int limit){
//        return repository.findAll(pageable);
//    }

    public FeedsResponse getFeeds(String name, int offset, int limit){
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Post> posts;
        if (name != null && !name.isEmpty()){
            posts = repository.findByTitle(name, pageable);
        } else {
            posts = repository.findAll(pageable);
        }

//        List<Integer> listPostId = posts.getContent().stream().filter()
//        List<PostComment> comments = commentRepository.getCommentsByList();
        List<PostComment> comments = new ArrayList<>(); //заглушка
        FeedsResponse response = new FeedsResponse(posts, comments);

        return response;
    }
}
