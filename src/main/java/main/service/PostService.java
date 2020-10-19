package main.service;

import main.data.request.PostRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
import main.data.response.type.PostDelete;
import main.data.response.type.PostInResponse;

public interface PostService {
    ListResponse<PostInResponse> getFeeds(String name, int offset, int itemPerPage);
    Response<PostInResponse> addNewPost(Integer personId, PostRequest request, Long pubDate);
    Response<PostInResponse> editPost(int id, Long pubDate, PostRequest request);
    Response<PostDelete> delPost(Integer id);
    ListResponse<PostInResponse> showWall(Integer personId, int offset, int itemsPerPage);

}
