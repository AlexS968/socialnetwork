package main.service;

import lombok.AllArgsConstructor;
import main.core.auth.JwtUtils;
import main.data.PersonPrincipal;
import main.data.request.LoginRequest;
import main.data.request.MeProfileRequest;
import main.data.response.InfoResponse;
import main.data.response.MeProfileResponse;
import main.data.response.MeProfileUpdateResponse;
import main.data.response.base.Response;
import main.data.response.type.InfoInResponse;
import main.data.response.type.MeProfile;
import main.data.response.type.MeProfileUpdate;
import main.data.response.type.ResponseMessage;
import main.data.response.type.PersonInLogin;
import main.model.City;
import main.model.Country;
import main.model.Person;
import main.repository.CityRepository;
import main.repository.CountryRepository;
import main.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Person user = personRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new PersonPrincipal(user);
    }

    public Response<PersonInLogin> login(LoginRequest request) {
        Authentication authentication
                = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        PersonPrincipal personPrincipal = (PersonPrincipal) authentication.getPrincipal();

        PersonInLogin personInLogin = new PersonInLogin(personPrincipal.getPerson());
        personInLogin.setToken(jwt);

        Person currentPerson = personPrincipal.getPerson();
        currentPerson.setLastOnlineTime(Instant.now());

        personRepository.save(currentPerson);

        return new Response<>(personInLogin);
    }

    public Response<ResponseMessage> logout() {
        return new Response<>(new ResponseMessage("ok"));
    }

    public MeProfileResponse getMe() {

        Person person = getCurrentPerson();

        MeProfileResponse response = new MeProfileResponse(); //+ поля тайм стем эрор
        MeProfile profile = new MeProfile(person); //+ поля из персон

        response.setData(profile);
        return response;

    }

    public MeProfileUpdateResponse putMe(MeProfileRequest updatedCurrentPerson) {
        Person personUpdated = personRepository.findById(getCurrentPerson().getId());
        personUpdated.setLastName(updatedCurrentPerson.getLastName());
        personUpdated.setFirstName(updatedCurrentPerson.getFirstName());
        personUpdated.setBirthDate(updatedCurrentPerson.getBirthDate());
        personUpdated.setPhone(updatedCurrentPerson.getPhone());
        personUpdated.setAbout(updatedCurrentPerson.getAbout());

        Country countryUpdated = countryRepository.findById(updatedCurrentPerson.getCountry());
        City cityUpdated = cityRepository.findById(updatedCurrentPerson.getCity());

        personUpdated.setCountry(countryUpdated);
        personUpdated.setCity(cityUpdated);

        personRepository.save(personUpdated);

        MeProfileUpdate updatedPerson = new MeProfileUpdate(personUpdated);
        MeProfileUpdateResponse response = new MeProfileUpdateResponse();
        response.setData(updatedPerson);
        return response;
    }


    public InfoResponse deleteMe() {

        int id = getCurrentPerson().getId();
        personRepository.deleteById(id);

        InfoInResponse info = new InfoInResponse("ok");
        InfoResponse response = new InfoResponse();
        response.setData(info);
        return response;

    }

    private Person getCurrentPerson() {
        return ((PersonPrincipal) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getPerson();
    }
}
