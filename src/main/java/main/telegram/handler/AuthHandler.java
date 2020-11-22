package main.telegram.handler;

import lombok.RequiredArgsConstructor;
import main.service.PersonService;
import main.service.RegistrationService;
import main.telegram.BotCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthHandler extends BaseHandler {
    private final PersonService personService;
    private final RegistrationService registrationService;

    @Override
    public List<SendMessage> handle(Update update) {
        List<SendMessage> messages = null;
        if (update.hasMessage()) {
            if (update.getMessage().hasText() && update.getMessage().getText().equals(BotCommand.LOGIN.toString())) {
                    messages = new ArrayList<>();
                    messages.add(login(update));
            } else if (update.getMessage().hasContact()) {
             messages = new ArrayList<>();
             messages.add(register(update));
            }
        }
        return messages;
    }

    private SendMessage login(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (!personService.loginTelegram(chatId)) {
            message.setReplyMarkup(getRegisterKeyboard());
            message.setText("Телефона в профиле нет ? Тогда не смогу войти");
        } else {
            message.setReplyMarkup(getKeyboard());
            message.setText("Мы внутри, можно смотреть уведомления b('w')b");
        }

        return message;
    }

    private SendMessage register(Update update) {
        Long chatId = update.getMessage().getChatId();
        String phone = update.getMessage().getContact().getPhoneNumber().substring(1);

        registrationService.registerTelegram(phone, chatId);
        SendMessage message = new SendMessage(chatId, "Теперь ты зареган! Можно входить b('w')b\nP.S. Да, отдельно");
        message.setReplyMarkup(getKeyboard());
        return message ;
    }

    private ReplyKeyboardMarkup getRegisterKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = getKeyboardTemplate();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton btn = new KeyboardButton("Регистрация");
        btn.setRequestContact(true);
        keyboardFirstRow.add(btn);

        keyboardFirstRow.add(new KeyboardButton("Помощь"));
        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
