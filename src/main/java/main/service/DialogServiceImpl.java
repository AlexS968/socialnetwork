package main.service;

import lombok.AllArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.PersonPrincipal;
import main.data.request.DialogAddRequest;
import main.data.request.DialogMessageRequest;
import main.data.request.ListRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
import main.data.response.type.*;
import main.model.Dialog;
import main.model.Message;
import main.model.Person;
import main.model.ReadStatus;
import main.repository.DialogRepository;
import main.repository.MessageRepository;
import main.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DialogServiceImpl implements DialogService {
    private final DialogRepository dialogRepository;
    private final PersonRepository personRepository;
    private final MessageRepository messageRepository;

    @Override
    public ListResponse<DialogList> list(ListRequest request) {
        PersonPrincipal currentUser = (PersonPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<DialogList> dialogs = new ArrayList<>();
        Pageable pageable;
        Page<Dialog> page;

        if (request.getItemPerPage() > 0) {
            pageable = new OffsetPageRequest(request.getOffset(), request.getItemPerPage(), Sort.unsorted());
        } else {
            pageable = Pageable.unpaged();
        }

        page = dialogRepository.findByPersons_id(currentUser.getPerson().getId(), pageable);

        page.forEach(i -> {
            DialogList item = new DialogList(i);

            item.setUnreadCount(messageRepository.countByReadStatusAndAuthor_idNotAndDialog_id(
                    ReadStatus.SENT,
                    currentUser.getPerson().getId(),
                    item.getId()
            ));

            Message lastMessage = messageRepository.findTopByDialog_idOrderByTimeDesc(item.getId());
            if (lastMessage != null) {
                DialogMessage dialogMessage = new DialogMessage(
                        lastMessage,
                        lastMessage.getAuthor().getId() == currentUser.getPerson().getId()
                );

                item.setLastMessage(dialogMessage);
            }

            dialogs.add(item);
        });

        return new ListResponse<>(
                dialogs,
                page.getTotalElements(),
                request.getOffset(),
                request.getItemPerPage()
        );
    }

    @Override
    public Response<DialogNew> add(DialogAddRequest request) {
        PersonPrincipal currentUser = (PersonPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Dialog dialog = new Dialog();

        List<Person> persons = new ArrayList<>();

        persons.add(personRepository.findById(currentUser.getPerson().getId()));

        request.getUserIds().forEach(i -> persons.add(personRepository.findById(i.intValue())));

        dialog.setPersons(persons);

        if (persons.size() > 1) {
           dialog.setPrimaryRecipient(persons.get(1));
        }

        dialogRepository.save(dialog);

        Response<DialogNew> response = new Response<>();
        DialogNew dialogNew = new DialogNew();
        dialogNew.setId(dialog.getId());
        response.setData(dialogNew);

        return response;
    }

    @Override
    public Response<DialogMessage> addMessage(int dialogId, DialogMessageRequest request) {
        PersonPrincipal currentUser = (PersonPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Dialog dialog = dialogRepository.findById(dialogId);

        Message message = new Message();
        message.setMessageText(request.getMessageText());
        message.setDialog(dialog);
        message.setTime(Instant.now());
        message.setAuthor(currentUser.getPerson());
        message.setRecipient(dialog.getPrimaryRecipient());
        message.setReadStatus(ReadStatus.SENT);
        messageRepository.save(message);

        Response<DialogMessage> response = new Response<>();
        response.setData(new DialogMessage(message));
        return response;
    }

    @Override
    public ListResponse<DialogMessage> listMessage(int dialogId, ListRequest request) {
        List<DialogMessage> messages = new ArrayList<>();
        Pageable pageable;
        Page<Message> page;

        if (request.getItemPerPage() > 0) {
            pageable = new OffsetPageRequest(request.getOffset(), request.getItemPerPage(), Sort.unsorted());
        } else {
            pageable = Pageable.unpaged();
        }

        page = messageRepository.findByDialog_id(dialogId, pageable);

        page.forEach(i -> {
            DialogMessage item = new DialogMessage(i);
            messages.add(item);
        });

        return new ListResponse<>(
                messages,
                page.getTotalElements(),
                request.getOffset(),
                request.getItemPerPage()
        );
    }

    @Override
    public Response<ResponseCount> countUnreadedMessage() {
        PersonPrincipal currentUser = (PersonPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        long count = messageRepository.countByReadStatusAndAuthor_idNotAndDialog_persons_id(
                ReadStatus.SENT,
                currentUser.getPerson().getId(),
                currentUser.getPerson().getId()
        );

        return new Response<>(new ResponseCount(count));
    }

    @Override
    public Response<ResponseMessage> setReadMessage(int messageId) {
        Message message = messageRepository.findById(messageId);
        message.setReadStatus(ReadStatus.READ);
        messageRepository.save(message);

        return new Response<>(new ResponseMessage("ok"));
    }
}
