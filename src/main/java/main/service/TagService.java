package main.service;

import main.data.request.TagRequest;
import main.data.response.ListTagResponse;
import main.data.response.TagCreateResponse;
import main.data.response.TagDeleteResponse;


public interface TagService {
    ListTagResponse getPostTags();
    ListTagResponse getPostTags(String tag);
    ListTagResponse getPostTags(String tag, Integer offset, Integer itemsPerPage);
    TagCreateResponse createTag(TagRequest request);
    TagDeleteResponse deleteTag(int id);
}
