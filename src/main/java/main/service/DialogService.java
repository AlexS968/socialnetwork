package main.service;

import main.data.request.DialogAddRequest;
import main.data.request.DialogMessageRequest;
import main.data.request.ListRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
import main.data.response.type.DialogList;
import main.data.response.type.DialogMessage;
import main.data.response.type.DialogNew;

import java.util.List;

public interface DialogService {
    ListResponse<DialogList> list(ListRequest request);
    Response<DialogNew> add(DialogAddRequest request);
    Response<DialogMessage> addMessage(int dialogId, DialogMessageRequest request);
    ListResponse<DialogMessage> listMessage(int dialogId, ListRequest request);
}
