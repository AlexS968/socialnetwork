package main.data.response.type;

import lombok.Data;
import main.model.Language;

@Data
public class LanguageInLanguageList {
    private int id;
    private String title;

    public LanguageInLanguageList(Language language) {
        id = language.getId();
        title = language.getTitle();
    }
}
