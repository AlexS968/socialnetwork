package main.service;


import lombok.AllArgsConstructor;
import main.data.request.RegistrationRequest;
import main.data.response.RegistrationResponse;
import main.data.response.type.DataMessage;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.MessagesPermission;
import main.model.Person;
import main.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
@AllArgsConstructor
public class RegistrationService {
    private final PersonRepository personRepository;
    private final CryptoService cryptoService;
    public RegistrationResponse registrationNewPerson(RegistrationRequest request){
        if (personRepository.findByEmail(request.getEmail()) != null) {
            throw new BadRequestException(new ApiError(
                    "invalid_request",
                    "такой email уже существует"
            ));
        }
        if (!(request.getPasswd1().equals(request.getPasswd2()))){
            throw new BadRequestException(new ApiError(
                    "invalid_request",
                    "пароли не совпадают"
            ));
        }
        if (!(request.getCode().equals("3675"))){
            throw new BadRequestException(new ApiError(
                    "invalid_request",
                    "код не совпадает"
            ));
        }
        Person person = new Person();
        person.setEmail(request.getEmail());
        person.setPasswordHash(String.valueOf(cryptoService.encode(request.getPasswd1())));
        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setRegDate(Instant.now());
        person.setMessagesPermission(MessagesPermission.ALL);
        person.setPhone("+71238492");
        person.setLastOnlineTime(Instant.now());
        personRepository.save(person);
        return new RegistrationResponse(
                "",
                LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                new DataMessage("ok")
        );
    }

}
