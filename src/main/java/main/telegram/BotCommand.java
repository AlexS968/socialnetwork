package main.telegram;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BotCommand {
    START("Старт", "/start"),
    HELP("Помощь", "/help"),
    NOTIFICATIONS("Уведомления", "/notifications"),
    SETTINGS("Настройки", "/settings"),
    REGISTER("Регистрация", "/register");

    private final String name;
    private final String command;
}
