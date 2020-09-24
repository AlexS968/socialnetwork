package main.service;

import lombok.AllArgsConstructor;
import main.core.auth.JwtUtils;
import main.data.PersonPrincipal;
import main.data.request.LoginRequest;
import main.data.request.MeProfileRequest;
import main.data.response.InfoResponse;
import main.data.response.LoginResponse;
import main.data.response.LogoutResponse;
import main.data.response.MeProfileResponse;
import main.data.response.MeProfileUpdateResponse;
import main.data.response.type.InfoInResponse;
import main.data.response.type.MeProfile;
import main.data.response.type.MeProfileUpdate;
import main.data.response.type.MessageInLogout;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements UserDetailsService {
    private final PersonRepository personRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Person user = personRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new PersonPrincipal(user);
    }

    public LoginResponse login(LoginRequest request) {

        System.out.println("---------encoded " + new BCryptPasswordEncoder().encode("12345678"));

        Authentication authentication
                = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        PersonPrincipal personPrincipal = (PersonPrincipal) authentication.getPrincipal();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setData(new PersonInLogin(personPrincipal.getPerson()));
        loginResponse.getData().setToken(jwt);

        return loginResponse;
    }

    public LogoutResponse logout() {
        return new LogoutResponse(new MessageInLogout("ok"));
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

        //personUpdated.setPhotoURL(updatedCurrentPerson.getPhotoURL());
        //personUpdated.setMessagesPermission(updatedCurrentPerson.getMessagesPermission());

        Country countryUpdated = countryRepository
            .findByCountryTitle(updatedCurrentPerson.getCountry());
        City cityUpdated = cityRepository
            .findByCityTitleAndCountryId(updatedCurrentPerson.getCity(), countryUpdated.getId());

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
