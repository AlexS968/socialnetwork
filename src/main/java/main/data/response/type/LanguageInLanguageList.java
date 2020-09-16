package main.data.response.type;

import lombok.Data;
import main.core.TextUtilities;
import main.model.Language;
import main.model.Post;

@Data
public class LanguageInLanguageList {
    private int id;
    private String title;

    public LanguageInLanguageList(Language language) {
        id = language.getId();
        title = language.getTitle();
    }
}
