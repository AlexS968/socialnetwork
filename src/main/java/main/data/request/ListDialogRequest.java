package main.data.request;

import lombok.Data;

@Data
public class ListDialogRequest {
    private String query;
    private int offset;
    private int itemPerPage;
}
