package main.service;

import lombok.AllArgsConstructor;
import main.data.request.PostRequest;
import main.data.response.FeedsResponse;
import main.data.response.ItemDeleteResponse;
import main.data.response.PostResponse;
import main.data.response.type.CommentInResponse;
import main.data.response.type.ItemDelete;
import main.data.response.type.PostInResponse;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.Person;
import main.model.Post;
import main.model.PostComment;
import main.model.PostTag;
import main.model.Tag;
import main.repository.PersonRepository;
import main.repository.PostCommentRepository;
import main.repository.PostRepository;
import main.repository.PostTagRepository;
import main.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final PersonRepository personRepository;
    private final PostCommentRepository commentRepository;
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

        Set<Integer> list = posts.getContent().stream().map(Post::getId).collect(Collectors.toSet());
        List<PostComment> comments = commentRepository.getCommentsByList(list);
        List<CommentInResponse> commentsList = comments.stream().map(CommentInResponse::new)
                .collect(Collectors.toList());

        return new FeedsResponse(posts, commentsList);
    }

    public PostResponse addNewPost(Integer personId, PostRequest request, Long pubDate) {
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
            response.setData(new PostInResponse(post, new ArrayList<>()));
        } catch (Exception ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Bad Request"));
        }

        return response;
    }

    private boolean checkPerson(Person person) {
        return person.getCity() != null || person.getCountry() != null || person.getAbout() != null;
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

    public ItemDeleteResponse delPost(Integer id) {
        ItemDeleteResponse response = new ItemDeleteResponse();

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
        response.setData(new ItemDelete(id));
        return response;
    }

    public <T> FeedsResponse<T> showWall(Integer personId) {
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

        Set<Integer> list = posts.getContent().stream().map(Post::getId).collect(Collectors.toSet());
        List<PostComment> comments = commentRepository.getCommentsByList(list);
        List<CommentInResponse> commentsList = comments.stream().map(CommentInResponse::new)
                .collect(Collectors.toList());

        return new FeedsResponse<>(posts, commentsList);
    }
}
