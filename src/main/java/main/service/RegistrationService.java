package main.service;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import main.data.request.RegistrationRequest;
import main.data.response.RegistrationResponse;
import main.data.response.type.DataMessage;
import main.data.response.type.GeoLocationResponseShort;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.City;
import main.model.Country;
import main.model.MessagesPermission;
import main.model.Person;
import main.repository.CityRepository;
import main.repository.CountryRepository;
import main.repository.PersonRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@AllArgsConstructor
public class RegistrationService {

  private final PersonRepository personRepository;
  private final CryptoService cryptoService;
  private final CountryRepository countryRepository;
  private final CityRepository cityRepository;
  private final JavaMailSender emailSender;
  private RestTemplate restTemplate;

  public RegistrationResponse registrationNewPerson(RegistrationRequest request, String ip) {
    if (personRepository.findByEmail(request.getEmail()) != null) {
      throw new BadRequestException(new ApiError(
          "invalid_request",
          "такой email уже существует"
      ));
    }
    if (!(request.getPasswd1().equals(request.getPasswd2()))) {
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

    if (ip.equals("0:0:0:0:0:0:0:1")) {
      person.setCity(cityRepository.findByCountryId(1, pageable).getContent().get(0));
      person.setCountry(countryRepository.findById(1));
    } else {

      GeoLocationResponseShort response = checkGeoData(ip);

      String countryFromIp = response.getCountryName();
      String cityFromIp = response.getCity();

      Optional<Country> countryOptional = countryRepository.findByTitle(countryFromIp);

      if (countryOptional.isEmpty()) {
        person.setCity(cityRepository.findByCountryId(1, pageable).getContent().get(0));
        person.setCountry(countryRepository.findById(1));
      } else {

        int countryId = countryOptional.get().getId();
        person.setCountry(countryOptional.get());

        List<Optional<City>> cityOptional = cityRepository
            .findByTitleAndCountryId(cityFromIp, countryId);

        if (cityOptional.isEmpty()) {

          City cityToSet = cityRepository.findFirstCityFromCountry(countryId);
          person.setCity(cityToSet);
        } else {
          person.setCity(cityOptional.get(0).get());
        }

      }


    }

    person.setAbout("");
    Date birthDate = new Date();
    birthDate.setTime(Instant.now().

        toEpochMilli());
    person.setBirthDate(birthDate);
    personRepository.save(person);

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(request.getEmail());
//        message.setSubject("Успешная регистрация");
//        message.setText("Вы успешно зарегестрированы в социальной сети");
//        emailSender.send(message);

    return new

        RegistrationResponse(
        "",
        LocalDateTime.now().

            toEpochSecond(ZoneOffset.UTC),
        new

            DataMessage("ok")
    );
  }

  private GeoLocationResponseShort checkGeoData(String ip) {

    String requestUrl = "http://api.ipstack.com/" + ip
        + "?access_key=1f3f0d1d66eaeb5f262fb6f2603da2ce&fields=country_name,city&language=ru";

    return restTemplate.getForObject(requestUrl, GeoLocationResponseShort.class);


  }

}


