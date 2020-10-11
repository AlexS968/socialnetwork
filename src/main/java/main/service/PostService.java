package main.service;

import lombok.AllArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.PersonPrincipal;
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
import main.repository.PostCommentRepository;
import main.repository.PostRepository;
import main.repository.PostTagRepository;
import main.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostCommentRepository commentRepository;
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

        Set<Integer> list = posts.getContent().stream().map(Post::getId).collect(Collectors.toSet());
        List<PostComment> comments = commentRepository.getCommentsByList(list);
        List<CommentInResponse> commentsList = comments.stream().map(CommentInResponse::new)
                .collect(Collectors.toList());

        int authId = getAuthUser().getId();

        return new FeedsResponse(posts, commentsList, authId);
    }

    @Transactional
    public PostResponse addNewPost(Integer personId, PostRequest request, Long pubDate) {
        Person person = getAuthUser(personId);

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
        final List<PostTag> postTags = (post == null) ? new ArrayList<>() : postToSave.getTags();
        final List<Tag> tags = new ArrayList<>();
        for (String s : postData.getTags()) {
            Optional<Tag> optionalTag = tagRepository.findTagByTag(s);
            Tag tag;
            if (optionalTag.isEmpty()) {
                tag = new Tag();
                tag.setTag(s);
            } else {
                tag = optionalTag.get();
            }
            PostTag postTag = new PostTag(postToSave, tag);
            postTags.add(postTag);
        }
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

    public ItemDeleteResponse delPost(Integer id) {
        ItemDeleteResponse response = new ItemDeleteResponse();

        Post post = getPresentedPost(id);
        //TODO check relatives post and tags
        try {
            postRepository.delete(post);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Ошибка удаления поста"));
        }
        response.setData(new ItemDelete(id));
        return response;
    }

    public FeedsResponse showWall(Integer personId, int offset, int itemsPerPage) {

        Person person = getAuthUser(personId);

        Pageable pageable = new OffsetPageRequest(offset, itemsPerPage, Sort.by("time").descending());

        Page<Post> posts = postRepository.findByAuthor(person, pageable);

        Set<Integer> list = posts.getContent().stream().map(Post::getId).collect(Collectors.toSet());
        List<PostComment> comments = commentRepository.getCommentsByList(list);
        List<CommentInResponse> commentsList = comments.stream().map(CommentInResponse::new)
                .collect(Collectors.toList());

        return new FeedsResponse(posts, commentsList, getAuthUser().getId());
    }

    @Transactional
    public PostResponse editPost(int id, Long pubDate, PostRequest request) {
        Post post = getPresentedPost(id);

            Person person = getAuthUser();
            post = savePost(post, request, person, pubDate);

            PostResponse response = new PostResponse();
            response.setData(new PostInResponse(post, new ArrayList<>())); //заглушка на комментарии
            return response;

    }

    private Post getPresentedPost(int id) {
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
