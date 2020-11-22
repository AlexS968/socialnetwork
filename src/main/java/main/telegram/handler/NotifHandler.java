package main.telegram.handler;

import lombok.RequiredArgsConstructor;
import main.core.ContextUtilities;
import main.service.NotificationService;
import main.telegram.BotCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotifHandler extends BaseHandler {

    private final NotificationService notificationService;
    @Override
    public List<SendMessage> handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText() ||
                !update.getMessage().getText().equals(BotCommand.NOTIFICATIONS.toString())) {
            return null;
        }
        List<SendMessage> messages = new ArrayList<>();
        SendMessage message = new SendMessage();
        message.setReplyMarkup(getKeyboard());
        try {
//            ContextUtilities.getCurrentPerson();
            notificationService.list(0, 10, false)
                    .getData()
                    .forEach(n -> messages.add(new SendMessage(update.getMessage().getChatId(), n.getInfo())));
        } catch (Exception ex){
         message.setText("Ну для начала я бы за логинился)");
        }
        messages.add(message);
        return messages;
    }
}
