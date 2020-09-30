package main.service;

import lombok.RequiredArgsConstructor;
import main.data.request.PasswordRecoveryRequest;
import main.data.request.PasswordSetRequest;
import main.data.response.InfoResponse;
import main.data.response.type.InfoInResponse;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.Person;
import main.repository.PersonRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final PersonRepository personRepository;
    private final JavaMailSender emailSender;
    private final PasswordEncoder encoder;

    @Value("${link}")
    public String link;

    @Override
    public InfoResponse restorePassword(PasswordRecoveryRequest request) {
        Person person = personRepository.findByEmail(request.getEmail());
        if (person == null) {
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
        message.setText(link + "?code=" + confirmationCode);
        emailSender.send(message);
        return new InfoResponse(new InfoInResponse("ok"));
    }

    public InfoResponse setPassword(PasswordSetRequest request, String referer) {
        Person person = personRepository.findByConfirmationCode(referer.split("=")[1]
        ).orElseThrow(() -> new BadRequestException(new ApiError(
                "invalid_request",
                "Аутентификация не пройдена.")));
        person.setPasswordHash(encoder.encode(request.getPassword()));
        personRepository.save(person);
        return new InfoResponse(new InfoInResponse("ok"));
    }
}
