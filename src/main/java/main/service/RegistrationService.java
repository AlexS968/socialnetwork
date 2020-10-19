package main.service;


import lombok.AllArgsConstructor;
import main.data.request.RegistrationRequest;
import main.data.response.RegistrationResponse;
import main.data.response.type.DataMessage;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.MessagesPermission;
import main.model.Person;
import main.repository.CityRepository;
import main.repository.CountryRepository;
import main.repository.PersonRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;


@Service
@AllArgsConstructor
public class RegistrationService {
    private final PersonRepository personRepository;
    private final CryptoService cryptoService;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final JavaMailSender emailSender;
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
        Person person = new Person();
        person.setEmail(request.getEmail());
        person.setPasswordHash(String.valueOf(cryptoService.encode(request.getPasswd1())));
        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setRegDate(Instant.now());
        person.setMessagesPermission(MessagesPermission.ALL);
        person.setPhone("Не установлен");
        Pageable pageable = Pageable.unpaged();
        person.setCity(cityRepository.findByCountryId(1,pageable).getContent().get(0));
        person.setCountry(countryRepository.findById(1));
        person.setAbout("");
        Date birthDate = new Date();
        birthDate.setTime(Instant.now().toEpochMilli());
        person.setBirthDate(birthDate);
        personRepository.save(person);

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(request.getEmail());
//        message.setSubject("Успешная регистрация");
//        message.setText("Вы успешно зарегестрированы в социальной сети");
//        emailSender.send(message);

        return new RegistrationResponse(
                "",
                LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                new DataMessage("ok")
        );
    }

}
