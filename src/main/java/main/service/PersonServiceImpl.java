package main.service;

import lombok.AllArgsConstructor;
import main.core.auth.JwtUtils;
import main.data.PersonPrincipal;
import main.data.request.LoginRequest;
import main.data.response.LoginResponse;
import main.data.response.LogoutResponse;
import main.data.response.type.MessageInLogout;
import main.data.response.type.PersonInLogin;
import main.model.Person;
import main.repository.PersonRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements UserDetailsService {
    private final PersonRepository personRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Person user = personRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new PersonPrincipal(user);
    }

    public LoginResponse login(LoginRequest request) {
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
}
