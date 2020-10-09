package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.Country;
import main.model.Dialog;

@Data
public class DialogList {
    private int id;
    @JsonProperty("unread_count")
    private int unreadCount;

    public DialogList(Dialog dialog) {
        id = dialog.getId();
    }
}

