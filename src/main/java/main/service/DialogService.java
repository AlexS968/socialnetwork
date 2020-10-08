package main.service;

import main.data.request.ListDialogRequest;
import main.data.response.ListDialogResponse;

public interface DialogService {
    ListDialogResponse list(ListDialogRequest request);
}
