package main.telegram.handler;

import lombok.RequiredArgsConstructor;
import main.telegram.BotCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class HelpHandler extends BaseHandler {

    @Override
    public List<SendMessage> handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText() ||
                !update.getMessage().getText().equals(BotCommand.HELP.toString())) {
            return null;
        }
        List<SendMessage> messages =  new ArrayList<>();
        String text = "Чтобы увидеть удеомления войди в аккаунт по кнопке 'Вход'. " +
                "Если аккаунт привязан, то получить уведомления можно по кнопке 'Уведомления'";
        SendMessage message = new SendMessage(update.getMessage().getChatId(), text);
        message.setReplyMarkup(getKeyboard());
        messages.add(message);
        return messages;
    }
}
