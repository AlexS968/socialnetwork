package main.service;

import lombok.AllArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.request.TagRequest;
import main.data.response.ListTagResponse;
import main.data.response.TagCreateResponse;
import main.data.response.TagDeleteResponse;
import main.data.response.type.SingleTag;
import main.model.Tag;
import main.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    @Override
    public ListTagResponse getPostTags() {
        return getPostTags(null);
    }

    @Override
    public ListTagResponse getPostTags(String tag) {
        return getPostTags(tag, null, null);
    }

    @Override
    public ListTagResponse getPostTags(String tag, Integer offset, Integer itemsPerPage) {
        if(!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new UsernameNotFoundException("invalid_request");
        }
        ListTagResponse listTagResponse = new ListTagResponse();

        if (offset == null || itemsPerPage == null) {
            Iterable<Tag> tagIterable = tag == null ? tagRepository.findAll() : tagRepository.findByTagLike(tag);
            for (Tag t: tagIterable) {
                listTagResponse.addTag(new SingleTag(t.getId(), t.getTag()));
            }
        } else {
            OffsetPageRequest offsetPageRequest = new OffsetPageRequest(offset, itemsPerPage, Sort.by("tag"));
            Page<Tag> tagsPage = tagRepository.findAll(offsetPageRequest);

            for (Tag page : tagsPage) {
                listTagResponse.addTag(new SingleTag(page.getId(), page.getTag()));
            }
        }

        return listTagResponse;
    }

    @Override
    public TagCreateResponse createTag(TagRequest request) {
        if(!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new UsernameNotFoundException("invalid_request");
        }
        Optional<Tag> optionalTag = tagRepository.findTagByTag(request.getTag());
        SingleTag singleTag;
        Tag tag;
        if (optionalTag.isPresent()) {
            tag = optionalTag.get();
        } else {
            tag = new Tag();
            tag.setTag(request.getTag());
            tag = tagRepository.save(tag);
        }
        singleTag = new SingleTag(tag.getId(), tag.getTag());
        return new TagCreateResponse(singleTag);
    }

    @Override
    public TagDeleteResponse deleteTag(int id) {
        if(!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new UsernameNotFoundException("invalid_request");
        }
        tagRepository.deleteById(id);
        return new TagDeleteResponse();
    }
}
