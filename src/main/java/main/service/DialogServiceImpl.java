package main.service;

import lombok.AllArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.PersonPrincipal;
import main.data.request.DialogAddRequest;
import main.data.request.ListDialogRequest;
import main.data.response.ListDialogResponse;
import main.data.response.base.Response;
import main.data.response.type.DialogInDialogList;
import main.data.response.type.DialogNew;
import main.model.Dialog;
import main.model.Person;
import main.repository.DialogRepository;
import main.repository.PersonRepository;
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
    private final PersonRepository personRepository;

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

    @Override
    public Response<DialogNew> add(DialogAddRequest request) {
        PersonPrincipal currentUser = (PersonPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Dialog dialog = new Dialog();

        List<Person> persons = new ArrayList<>();

        persons.add(personRepository.findById(currentUser.getPerson().getId()));

        request.getUserIds().forEach(i -> {
            persons.add(personRepository.findById(i.intValue()));
        });

        dialog.setPersons(persons);
        dialogRepository.save(dialog);

        Response<DialogNew> response = new Response<>();
        DialogNew dialogNew = new DialogNew();
        dialogNew.setId(dialog.getId());
        response.setData(dialogNew);

        return response;
    }
}
