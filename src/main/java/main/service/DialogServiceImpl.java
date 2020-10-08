package main.service;

import lombok.AllArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.PersonPrincipal;
import main.data.request.ListDialogRequest;
import main.data.response.ListDialogResponse;
import main.data.response.type.DialogInDialogList;
import main.model.Dialog;
import main.repository.DialogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DialogServiceImpl implements DialogService {
    private final DialogRepository dialogRepository;

    @Override
    public ListDialogResponse list(ListDialogRequest request) {
        PersonPrincipal currentUser = (PersonPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<DialogInDialogList> dialogs = new ArrayList<>();
        Pageable pageable;
        Page<Dialog> page;

        if (request.getItemPerPage() > 0) {
            pageable = new OffsetPageRequest(request.getOffset(), request.getItemPerPage(), Sort.unsorted());
        } else {
            pageable = Pageable.unpaged();
        }

        page = dialogRepository.findByPersons_id(currentUser.getPerson().getId(), pageable);

        page.forEach(i -> {
            DialogInDialogList item = new DialogInDialogList(i);
            dialogs.add(item);
        });

        return new ListDialogResponse(dialogs);
    }
}
