package main.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final PersonRepository personRepository;
    private final JavaMailSender emailSender;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    //send link to restore password
    @Override
    public InfoResponse restorePassword(PasswordRecoveryRequest request, String link) {
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
        message.setSubject("Ссылка на восстановление пароля на SocialNetwork (group 8)");
        message.setText(link + "?code=" + confirmationCode);
        emailSender.send(message);
        return new InfoResponse(new InfoInResponse("ok"));
    }

    //set new password by restoring or changing
    @Override
    public InfoResponse setPassword(PasswordSetRequest request, String referer) {
        Person person;
        //if password is restored (person is unauthenticated)
        if (SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString().equals("anonymousUser")) {
            person = personRepository.findByConfirmationCode(referer.split("=")[1]
            ).orElseThrow(() -> new BadRequestException(new ApiError(
                    "invalid_request",
                    "Аутентификация не пройдена.")));
        }
        //if password is changed (person is authenticated)
        else {
            //check token validity
            if (jwtUtils.validateJwtToken(request.getToken())) {
                person = ((PersonPrincipal) SecurityContextHolder.getContext().
                        getAuthentication().getPrincipal()).getPerson();
            } else throw new BadRequestException(new ApiError(
                    "invalid_request",
                    "Аутентификация не пройдена."));
        }
        person.setPasswordHash(encoder.encode(request.getPassword()));
        personRepository.save(person);
        return new InfoResponse(new InfoInResponse("ok"));
    }

    //send link to change password or email address
    @Override
    public InfoResponse changePassOrEmail(String subject, String link) {
        Person person = ((PersonPrincipal) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getPerson();

        String confirmationCode = RandomStringUtils.randomAlphanumeric(45);
        person.setConfirmationCode(confirmationCode);
        personRepository.save(person);
        // send link by email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(person.getEmail());
        message.setSubject("Ссылка на изменение ".concat(subject)
                .concat(" на SocialNetwork (group 8)"));
        message.setText(link + "?code=" + confirmationCode);
        emailSender.send(message);
        return new InfoResponse(new InfoInResponse("ok"));
    }

    //set new email address
    @Override
    public InfoResponse setEmail(PasswordRecoveryRequest request) {
        Person person = ((PersonPrincipal) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getPerson();

        person.setEmail(request.getEmail());
        personRepository.save(person);
        // send confirmation to new email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getEmail());
        message.setSubject("Подтверждение изменения email на SocialNetwork (group 8)");
        message.setText("Ваш email успешно изменен.");
        emailSender.send(message);
        return new InfoResponse(new InfoInResponse("ok"));
    }
}

