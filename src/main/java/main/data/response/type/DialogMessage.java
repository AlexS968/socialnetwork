package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.Message;
import main.model.ReadStatus;

@Data
public class DialogMessage {
    private int id;
    private long time;

    @JsonProperty("author_id")
    private int authorId;

    @JsonProperty("recipient_id")
    private int recipientId;

    @JsonProperty("message_text")
    private String messageText;

    @JsonProperty("read_status")
    private ReadStatus readStatus;

    public DialogMessage(Message message) {
        this.id = message.getId();
        this.time = message.getTime().toEpochMilli();
        this.authorId = message.getAuthor().getId();
        this.recipientId = (message.getRecipient() != null) ? message.getRecipient().getId() : null;
        this.messageText = message.getMessageText();
        this.readStatus = message.getReadStatus();
    }
}
