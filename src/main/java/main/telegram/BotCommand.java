package main.telegram;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BotCommand {
    START ("/start"),
    HELP ("Помощь"),
    NOTIFICATIONS ("Уведомления"),
    SETTINGS ("Настройки"),
    REGISTER("Регистрация");

    private final String name;

    public String toString() {
        return this.name;
    }
}
