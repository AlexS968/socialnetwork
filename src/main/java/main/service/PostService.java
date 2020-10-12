package main.service;

import lombok.AllArgsConstructor;
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
import main.repository.PersonRepository;
import main.repository.PostRepository;
import main.repository.PostTagRepository;
import main.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final PersonRepository personRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    public FeedsResponse getFeeds(String name, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by("time").descending());
        Page<Post> posts;
        if (name != null && !name.isEmpty()) {
            posts = repository.findByTitle(name, pageable);
        } else {
            posts = repository.findAll(pageable);
        }

        return new FeedsResponse(posts);
    }

    public ResponseEntity<?> addNewPost(Integer personId, PostRequest request, Long pubDate) {
        //TODO добавить проверку авторизации
        //return new ResponseEntity<>(new ApiError("invalid_request", "Неавторизованный пользователь"), HttpStatus.UNAUTHORIZED);
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isEmpty()) {
            throw new BadRequestException(new ApiError("invalid_request", "Пользователь не существует"));
        }
        Person person = personOptional.get();
        if (!checkPerson(person)) {
            throw new BadRequestException(
                    new ApiError("invalid_request", "Профиль пользователя не заполнен")
            );
        }
        Post post = savePost(null, request, person, pubDate);
        PostResponse response = new PostResponse();
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
        final List<PostTag> postTags = new ArrayList<>();
        final List<Tag> tags = new ArrayList<>();
        for (String s : postData.getTags()) {
            Tag tag = new Tag();
            tag.setTag(s);

            PostTag postTag = new PostTag(postToSave, tag);
            postTags.add(postTag);
        }
        postToSave.setTitle(postData.getTitle());
        postToSave.setPostText(postData.getPostText());
        postToSave.setTags(postTags);
        postToSave.setTime(postTime);
        postToSave.setAuthor(person);
        tagRepository.saveAll(tags);
        postToSave = repository.save(postToSave);
        postTagRepository.saveAll(postTags);
        return postToSave;
    }

    public ResponseEntity<?> delPost(Integer id) {
        PostDeleteResponse result = new PostDeleteResponse();

        Optional<Post> postOptional = repository.findById(id);
        if (postOptional.isEmpty()) {
            throw new BadRequestException(new ApiError("invalid_request", "Пост не существует"));
        }
        Post post = postOptional.get();
        try {
            repository.delete(post);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Ошибка удаления поста"));
        }
        result.setData(new PostDelete(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<?> showWall(Integer personId) {
        //TODO добавить проверку авторизации
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isEmpty()) {
            throw new BadRequestException(new ApiError("invalid_request", "Пользователь не существует"));
        }
        Person person = personOptional.get();

        int offset = 0;
        int limit = 10;

        Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by("time").descending());

        Page<Post> posts = repository.findByAuthor(person, pageable);

        FeedsResponse result = new FeedsResponse(posts);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
