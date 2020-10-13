package main.service;

import lombok.AllArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.PersonPrincipal;
import main.data.request.PostRequest;
import main.data.response.FeedsResponse;
import main.data.response.PostDeleteResponse;
import main.data.response.PostResponse;
import main.data.response.type.PostDelete;
import main.data.response.type.PostInResponse;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.Person;
import main.model.Post;
import main.model.PostTag;
import main.model.Tag;
import main.repository.PostRepository;
import main.repository.PostTagRepository;
import main.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    public FeedsResponse getFeeds(String name, int offset, int limit) {
        Pageable pageable = new OffsetPageRequest(offset, limit, Sort.by("time").descending());
        Page<Post> posts;
        if (name != null && !name.isEmpty()) {
            posts = postRepository.findByTitle(name, pageable);
        } else {
            posts = postRepository.findAll(pageable);
        }

        return new FeedsResponse(posts, getAuthUser().getId());
    }

    @Transactional
    public ResponseEntity<?> addNewPost(Integer personId, PostRequest request, Long pubDate) {
        Person person = getAuthUser(personId);

        if (!checkPerson(person)) {
            throw new BadRequestException(
                    new ApiError("invalid_request", "Профиль пользователя не заполнен")
            );
        }
        Post post = savePost(null, request, person, pubDate);
        PostResponse response;
        try {
            response = postResponseMapper(post);
        } catch (Exception ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Bad Request"));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean checkPerson(Person person) {
        return person.getCity() != null || person.getCountry() != null || person.getAbout() != null;
    }

    private PostResponse postResponseMapper(Post post) {
        PostResponse result = new PostResponse();

        result.setError("ok");
        result.setTimestamp(Instant.now().toEpochMilli());
        result.setData(new PostInResponse(post));

        return result;
    }

    private Post savePost(Post post, PostRequest postData, Person person, Long pubDate) {
        Post postToSave = (post == null) ? new Post() : post;
        final Instant postTime = pubDate == null ? Instant.now() : Instant.ofEpochMilli(pubDate);
        final List<PostTag> postTags = (post == null) ? new ArrayList<>() : postToSave.getTags();
        final List<Tag> tags = new ArrayList<>();
        for (String s : postData.getTags()) {
            Optional<Tag> optionalTag = tagRepository.findTagByTag(s);
            Tag tag;
            if (optionalTag.isEmpty()) {
                tag = new Tag();
                tag.setTag(s.trim());
            } else {
                tag = optionalTag.get();
            }
            tags.add(tag);
            PostTag postTag = new PostTag(postToSave, tag);
            if (!postTags.contains(postTag)) {
                postTags.add(postTag);
            }
        }
        postTags.removeIf(pt -> !tags.contains(pt.getTag()));
        postToSave.setTitle(postData.getTitle());
        postToSave.setPostText(postData.getPostText());
        postToSave.setTime(postTime);
        postToSave.setAuthor(person);
        tagRepository.saveAll(tags);
        postToSave = postRepository.save(postToSave);
        postTagRepository.saveAll(postTags);
        return postToSave;
    }

    public ResponseEntity<?> delPost(Integer id) {
        PostDeleteResponse result = new PostDeleteResponse();
        Post post = getPost(id);
        try {
            postRepository.delete(post);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Ошибка удаления поста"));
        }
        result.setData(new PostDelete(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<?> showWall(Integer personId, int offset, int itemsPerPage) {
        Person person = getAuthUser(personId);

        Pageable pageable = new OffsetPageRequest(offset, itemsPerPage, Sort.by("time").descending());

        Page<Post> posts = postRepository.findByAuthor(person, pageable);

        FeedsResponse result = new FeedsResponse(posts, getAuthUser().getId());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Transactional
    public PostResponse editPost(int id, Long pubDate, PostRequest request) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            Person person = getAuthUser();
            post = savePost(post, request, person, pubDate);

            PostResponse response = new PostResponse();
            response.setData(new PostInResponse(post));
            return response;
        } else {
            throw new BadRequestException(new ApiError("invalid_request", "Пост не существует"));
        }
    }

    private Post getPost(int id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            throw new BadRequestException(new ApiError("invalid_request", "Пост не существует"));
        }
        return postOptional.get();
    }

    private Person getAuthUser() {
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new UsernameNotFoundException("invalid_request");
        }
        return ((PersonPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson();
    }

    private Person getAuthUser(int id) {
        Person person = getAuthUser();
        if (person.getId() != id) {
            throw new UsernameNotFoundException("invalid_request");
        }
        return person;
    }
}
