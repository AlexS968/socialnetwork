package main.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import main.data.response.base.ListResponse;
import main.data.response.type.CountryInCountryList;
import main.data.response.type.DialogInDialogList;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListDialogResponse extends ListResponse {
    private List<DialogInDialogList> data;

    public ListDialogResponse(List<DialogInDialogList> dialogs) {
        data = dialogs;
    }

    public void add(List<DialogInDialogList> dialogs) {
        data.addAll(dialogs);
    }
}

