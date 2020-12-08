package main.telegram.handler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import main.config.SpringFoxConfig;
import main.telegram.BotCommand;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Profile("prod")
@Component
@RequiredArgsConstructor
@Api(tags = {SpringFoxConfig.BOT_TAG})
public class ReplyHandler extends BaseHandler {

    @ApiOperation("Работа с общими сообщениями")
    @Override
    public List<SendMessage> handle(Update update) {
        List<SendMessage> messages = new ArrayList<>();
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return messages;
        }
        String text = update.getMessage().getText();
        for (BotCommand command : BotCommand.values()) {
            if (text.equals(command.getName()) || text.equals(command.getCommand())) {
                return messages;
            }
        }
        SendMessage message = new SendMessage(update.getMessage().getChatId(), "Просто так не отвечаю, выбери команду!");
        message.setReplyMarkup(getReplyKeyboard());
        messages.add(message);
        return messages;
    }
}
