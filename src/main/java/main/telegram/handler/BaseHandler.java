package main.telegram.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHandler {
    public abstract List<SendMessage> handle(Update update);

    protected ReplyKeyboardMarkup getKeyboardTemplate() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }

    protected ReplyKeyboardMarkup getGeneralKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = getKeyboardTemplate();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Уведомления"));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Настройки"));
        keyboardSecondRow.add(new KeyboardButton("Помощь"));


        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    protected ReplyKeyboardMarkup getRegisterKeyboard() {
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