package main.service;

import lombok.AllArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.PersonPrincipal;
import main.data.request.PostRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
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

    public ListResponse<PostInResponse> getFeeds(String name, int offset, int itemPerPage) {
        Pageable pageable = new OffsetPageRequest(offset, itemPerPage, Sort.by("time").descending());
        Page<Post> postPage;
        if (name != null && !name.isEmpty()) {
            postPage = postRepository.findByTitle(name, pageable);
        } else {
            postPage = postRepository.findAll(pageable);
        }
        return new ListResponse<>(extractPage(postPage), postPage.getTotalElements(), offset, itemPerPage);
    }

    @Transactional
    public Response<PostInResponse> addNewPost(Integer personId, PostRequest request, Long pubDate) {
        try {
            Person person = getAuthUser(personId);
            Post post = savePost(null, request, person, pubDate);
            return new Response<>(new PostInResponse(post));
        } catch (Exception ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Ошибка создания поста"));
        }
    }

    @Transactional
    public Response<PostInResponse> editPost(int id, Long pubDate, PostRequest request) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            Person person = getAuthUser();
            post = savePost(post, request, person, pubDate);

            return new Response<>(new PostInResponse(post));
        } else {
            throw new BadRequestException(new ApiError("invalid_request", "Пост не существует"));
        }
    }

    public Response<PostDelete> delPost(Integer id) {
        try {
            Post post = getPost(id);
            postRepository.delete(post);
            return new Response<>(new PostDelete(id));
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Ошибка удаления поста"));
        }
    }

    public ListResponse<PostInResponse>  showWall(Integer personId, int offset, int itemsPerPage) {
        Person person = getAuthUser(personId);
        Pageable pageable = new OffsetPageRequest(offset, itemsPerPage, Sort.by("time").descending());
        Page<Post> postPage = postRepository.findByAuthor(person, pageable);

        return new ListResponse<>(extractPage(postPage), postPage.getTotalElements(), offset, itemsPerPage);
    }
//-----------------------
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
        postToSave.setTags(postTags);
        postToSave.setTime(postTime);
        postToSave.setAuthor(person);
        tagRepository.saveAll(tags);
        postToSave = postRepository.save(postToSave);
        postTagRepository.saveAll(postTags);
        return postToSave;
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

    private List<PostInResponse> extractPage(Page<Post> postPage) {
        List<PostInResponse> posts = new ArrayList<>();
        for (Post item : postPage.getContent()) {
            PostInResponse postInResponse = new PostInResponse(item);
            if (item.getTime().isBefore(Instant.now())) {
                postInResponse.setType(PostType.POSTED);
            } else {
                postInResponse.setType(PostType.QUEUED);
            }
            if (!(postInResponse.getType() == PostType.QUEUED && postInResponse.getAuthor().getId() != getAuthUser().getId())) {
                posts.add(postInResponse);
            }
        }
        return posts;
    }
}
