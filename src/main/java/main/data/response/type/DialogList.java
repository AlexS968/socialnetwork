package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.Dialog;

@Data
public class DialogList {
    private int id;
    @JsonProperty("unread_count")
    private long unreadCount;
    @JsonProperty("last_message")
    private DialogMessage lastMessage;

    public DialogList(Dialog dialog) {
        id = dialog.getId();
    }
}

