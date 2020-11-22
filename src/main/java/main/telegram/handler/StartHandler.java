package main.telegram.handler;

import lombok.RequiredArgsConstructor;
import main.telegram.BotCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StartHandler extends BaseHandler {

    @Override
    public List<SendMessage> handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText() ||
                !update.getMessage().getText().equals(BotCommand.START.toString())) {
            return null;
        }
        User user = update.getMessage().getFrom();
        List<SendMessage> messages = new ArrayList<>();
        SendMessage message = new SendMessage();
        message.setText(String.format("Привет, %s! Для входа нажми 'Вход'", user.getUserName()));
        message.setReplyMarkup(getKeyboard());
        messages.add(message);
        return messages;
    }
}
