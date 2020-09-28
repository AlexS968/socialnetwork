package main.service;

import lombok.AllArgsConstructor;
import main.core.auth.JwtUtils;
import main.data.PersonPrincipal;
import main.data.request.PasswordRecoveryRequest;
import main.data.request.PasswordSetRequest;
import main.data.response.InfoResponse;
import main.data.response.type.InfoInResponse;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.Person;
import main.repository.PersonRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final PersonRepository personRepository;
    private final JwtUtils jwtUtils;
    private final JavaMailSender emailSender;
    private final PasswordEncoder encoder;

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
        message.setText("http://localhost:8080/change-password?text=" + confirmationCode);
        emailSender.send(message);
        return new InfoResponse(new InfoInResponse("ok"));
    }

    public InfoResponse setPassword(PasswordSetRequest request) {
        // если пользователь неавторизован, проверяем по секретному коду, направленному в email
        Person person = personRepository.findByConfirmationCode(
                encoder.encode(request.getToken()));
        // в противном случае сверяем токен из реквеста с токеном текущего пользователя
        if (person == null & jwtUtils.validateJwtToken(request.getToken())) {
            person = ((PersonPrincipal) SecurityContextHolder.getContext().
                    getAuthentication().getPrincipal()).getPerson();
        } else {
            // если оба варианта не подошли - выбрасываем исключение
            throw new BadRequestException(new ApiError(
                    "invalid_request",
                    "Аутентификация не пройдена."));
        }
        // если проверка успешна - меняем пароль
        person.setPasswordHash(encoder.encode(request.getPassword()));
        personRepository.save(person);
        return new InfoResponse(new InfoInResponse("ok"));
    }
}
