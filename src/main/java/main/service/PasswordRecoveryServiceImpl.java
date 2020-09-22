package main.service;

import lombok.AllArgsConstructor;
import main.data.request.PasswordRecoveryRequest;
import main.data.response.PasswordRecoveryResponse;
import main.data.response.type.DataMessage;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.Person;
import main.repository.PersonRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {
    private final PersonRepository personRepository;
    private final JavaMailSender emailSender;

    @Override
    public PasswordRecoveryResponse restorePassword(PasswordRecoveryRequest request) {
        Person person = personRepository.findByEmail(request.getEmail());
        if (person == null){
            throw new BadRequestException(new ApiError(
                    "invalid_request",
                    "Такой email не зарегистрирован"));
        }
        String confirmationCode = RandomStringUtils.randomAlphanumeric(45);
        person.setConfirmationCode(confirmationCode);
        personRepository.save(person);
        // send link by email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getEmail());
        message.setSubject("Ссылка на восстановление пароля");
        message.setText("http://localhost:8080/login/change-password/" + confirmationCode);
        emailSender.send(message);
        return new PasswordRecoveryResponse(
                "",
                LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                new DataMessage("ok"));
    }
}
