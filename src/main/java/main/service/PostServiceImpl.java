package main.service;

import lombok.RequiredArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.request.PostRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
import main.data.response.type.CommentInResponse;
import main.data.response.type.PostDelete;
import main.data.response.type.PostInResponse;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.*;
import main.repository.PostRepository;
import main.repository.PostTagRepository;
import main.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final CommentService commentService;
    private final PersonService personService;
    private final NotificationService notificationService;

    @Override
    public ListResponse<PostInResponse> getFeeds(String name, int offset, int itemPerPage) {
        Pageable pageable = new OffsetPageRequest(offset, itemPerPage, Sort.by("time").descending());
        Page<Post> postPage;
        if (name != null && !name.isEmpty()) {
            postPage = postRepository.findByTitle(name, pageable);
        } else {
            postPage = postRepository.findAll(pageable);
        }
        List<CommentInResponse> commentsList = commentService.getCommentsList(postPage.getContent());
        return new ListResponse<>(extractPage(postPage, commentsList), postPage.getTotalElements(), offset, itemPerPage);
    }

    @Override
    @Transactional
    public Response<PostInResponse> addNewPost(Integer personId, PostRequest request, Long pubDate) {
        try {
            Person person = personService.getById(personId);
            Post post = savePost(null, request, person, pubDate);

            notificationService.setNotification(post);

            return new Response<>(new PostInResponse(post, new ArrayList<>(), personId));
        } catch (Exception ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Ошибка создания поста"));
        }
    }

    @Override
    @Transactional
    public Response<PostInResponse> editPost(int id, Long pubDate, PostRequest request) {
        Post post = findById(id);

        Person person = personService.getAuthUser();
        post = savePost(post, request, person, pubDate);

        return new Response<>(new PostInResponse(post, new ArrayList<>(), person.getId()));

    }

    @Override
    public Response<PostDelete> delPost(Integer id) {
        try {
            Post post = findById(id);
            notificationService.deleteNotification(post);
            postRepository.delete(post);
            return new Response<>(new PostDelete(id));
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Ошибка удаления поста"));
        }
    }

    @Override
    public ListResponse<PostInResponse> showWall(Integer personId, int offset, int itemsPerPage) {
        personService.isAuthenticated();
        Person person = personService.getById(personId);
        Pageable pageable = new OffsetPageRequest(offset, itemsPerPage, Sort.by("time").descending());
        Page<Post> postPage = postRepository.findByAuthor(person, pageable);
        List<CommentInResponse> commentsList = commentService.getCommentsList(postPage.getContent());
        return new ListResponse<>(extractPage(postPage, commentsList), postPage.getTotalElements(), offset, itemsPerPage);
    }

    //-----------------------
    private Post savePost(Post post, PostRequest postData, Person person, Long pubDate) {
        Post postToSave = (post == null) ? new Post() : post;
        final Instant postTime = pubDate == null ? Instant.now() :
                Instant.ofEpochMilli(pubDate).isBefore(Instant.now()) ? Instant.now() : Instant.ofEpochMilli(pubDate);
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
        postToSave.setTags(postTags);
        postToSave.setTime(postTime);
        postToSave.setAuthor(person);
        tagRepository.saveAll(tags);
        postToSave = postRepository.save(postToSave);
        postTagRepository.saveAll(postTags);
        return postToSave;
    }

    @Override
    public Post getPost(int id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            throw new BadRequestException(new ApiError("invalid_request", "Пост не существует"));
        }
        return postOptional.get();
    }

    @Override
    public Post findById(int id) {
        return postRepository.findById(id).orElseThrow(
                () -> new BadRequestException(new ApiError(
                        "invalid request",
                        "Пост не существует")));
    }

    @Override
    public List<Post> findByAuthor(Person author){
        return  postRepository.findByAuthor(author);
    }

    private List<PostInResponse> extractPage(Page<Post> postPage, List<CommentInResponse> comments) {
        List<PostInResponse> posts = new ArrayList<>();
        int userId = personService.getAuthUser().getId();
        for (Post item : postPage.getContent()) {
            PostInResponse postInResponse = new PostInResponse(item, comments, 0); // need a Person?
            if (item.getTime().isBefore(Instant.now())) {
                postInResponse.setType(PostType.POSTED);
            } else {
                postInResponse.setType(PostType.QUEUED);
            }
            if (!(postInResponse.getType() == PostType.QUEUED && postInResponse.getAuthor().getId() != personService.getAuthUser().getId())) {
                posts.add(postInResponse);
            }
        }
        return posts;
    }
}
