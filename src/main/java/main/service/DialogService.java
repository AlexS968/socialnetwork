package main.service;

import main.data.request.DialogAddRequest;
import main.data.request.ListDialogRequest;
import main.data.response.ListDialogResponse;
import main.data.response.base.Response;
import main.data.response.type.DialogNew;

public interface DialogService {
    ListDialogResponse list(ListDialogRequest request);
    Response<DialogNew> add(DialogAddRequest request);
}
