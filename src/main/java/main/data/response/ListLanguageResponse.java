package main.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import main.data.response.base.ListResponse;
import main.data.response.type.LanguageInLanguageList;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListLanguageResponse extends ListResponse {
    private List<LanguageInLanguageList> data;

    public ListLanguageResponse(List<LanguageInLanguageList> languages) {
        data = languages;
    }

    public void add(List<LanguageInLanguageList> languages) {
        data.addAll(languages);
    }
}
