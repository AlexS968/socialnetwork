package main.service;

import lombok.AllArgsConstructor;
import main.core.auth.JwtUtils;
import main.data.PersonPrincipal;
import main.data.request.LoginRequest;
import main.data.request.MeProfileRequest;
import main.data.response.base.Response;
import main.data.response.type.*;
import main.model.City;
import main.model.Country;
import main.model.Person;
import main.repository.CityRepository;
import main.repository.CountryRepository;
import main.repository.PersonRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements UserDetailsService, PersonService {

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

    @Override
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

    @Override
    public Response<ResponseMessage> logout() {
        return new Response<>(new ResponseMessage("ok"));
    }

    @Override
    public Response<MeProfile> getMe() {

        Person person = getCurrentPerson();
        Response<MeProfile> response = new Response<>();
        MeProfile profile = new MeProfile(person);
        response.setData(profile);
        return response;

    }

    @Override
    public Response<MeProfileUpdate> putMe(MeProfileRequest updatedCurrentPerson) {
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
        Response<MeProfileUpdate> response = new Response<>();
        response.setData(updatedPerson);
        return response;
    }

    @Override
    public Response<InfoInResponse> deleteMe() {

        int id = getCurrentPerson().getId();
        personRepository.deleteById(id);

        InfoInResponse info = new InfoInResponse("ok");
        Response<InfoInResponse> response = new Response<>();
        response.setData(info);
        return response;

    }

    @Override
    public Person getCurrentPerson() {
        return ((PersonPrincipal) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getPerson();
    }


    @Override
    public Person getById(int personId) {
        return personRepository.findById(personId);
    }

    public boolean isAuthenticated() {
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new UsernameNotFoundException("invalid_request");
        }
        return true;
    }

    public Person getAuthUser() {
        isAuthenticated();
        return getCurrentPerson();
    }

    public Person checkAuthUser(int id) {
        Person person = getAuthUser();
        if (person.getId() != id) {
            throw new UsernameNotFoundException("invalid_request");
        }
        return person;
    }

    public Response<MeProfile> getProfile(Integer id) {
        Person person = new Person();
        Optional<Person> personOpt = personRepository.findById(id);
        if (personOpt.isPresent()) {
            person = personOpt.get();
        } else {
            throw new UsernameNotFoundException("invalid_request");
        }

        Response<MeProfile> response = new Response<>();

        MeProfile profile = new MeProfile(person);
        response.setData(profile);
        return response;
    }
}

