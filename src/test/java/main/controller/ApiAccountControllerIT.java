package main.controller;

import main.AbstractIntegrationIT;
import main.core.auth.JwtUtils;
import main.data.request.PasswordRecoveryRequest;
import main.data.request.PasswordSetRequest;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiAccountControllerIT extends AbstractIntegrationIT {

    @MockBean
    JwtUtils jwtUtils;

    @Value("${linkToChange.password}")
    public String passwordChangeLink;

    @Test
    //@WithUserDetails(value = "user@user.ru")
    public void shouldSetPasswordWhenPersonIsUnauthenticated() throws Exception {
        PasswordSetRequest request = new PasswordSetRequest();
        request.setToken("");
        request.setPassword("12345678");
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.REFERER, passwordChangeLink + "?code=referer");

        mockMvc.perform(put("/api/v1/account/password/set")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andExpect(jsonPath("$.data.message").value("ok"));
    }

    @Test
    @WithUserDetails(value = "user@user.ru")
    public void shouldSetPasswordWhenPersonIsAuthenticated() throws Exception {
        PasswordSetRequest request = new PasswordSetRequest();
        request.setToken("");
        request.setPassword("12345678");
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request);

        Mockito.when(jwtUtils.validateJwtToken(request.getToken())).thenReturn(true);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.REFERER, "");

        mockMvc.perform(put("/api/v1/account/password/set")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(jsonPath("$.data.message").value("ok"));
    }

    @Test
    public void shouldRestorePasswordWhenEmailIsCorrect() throws Exception {
        PasswordRecoveryRequest request = new PasswordRecoveryRequest();
        request.setEmail("user@user.ru");
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request);

        mockMvc.perform(put("/api/v1/account/password/recovery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andExpect(jsonPath("$.data.message").value("ok"));
    }

    @Test
    public void shouldReturnBadRequestWhenEmailIsIncorrect() throws Exception {
        PasswordRecoveryRequest request = new PasswordRecoveryRequest();
        request.setEmail("person@user.ru");
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request);

        mockMvc.perform(put("/api/v1/account/password/recovery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(unauthenticated())
                .andExpect(jsonPath("$.error").value("invalid_request"))
                .andExpect(jsonPath("$.error_description").value("Такой email не зарегистрирован"));
    }

    @Test
    @WithUserDetails(value = "user@user.ru")
    public void shouldSendLinkToChangePassword() throws Exception {

        mockMvc.perform(put("/api/v1/account/password/change"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(jsonPath("$.data.message").value("ok"));
    }

    @Test
    public void shouldReturnForbiddenWhenUnauthenticatedByChangePassword() throws Exception {

        mockMvc.perform(put("/api/v1/account/password/change"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithUserDetails(value = "user@user.ru")
    public void shouldSendLinkToChangeEmail() throws Exception {

        mockMvc.perform(put("/api/v1/account/email/change"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(jsonPath("$.data.message").value("ok"));
    }

    @Test
    public void shouldReturnForbiddenWhenUnauthenticatedByChangeEmail() throws Exception {

        mockMvc.perform(put("/api/v1/account/email/change"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

    @Test
    @WithUserDetails(value = "user@user.ru")
    public void shouldSetEmailWhenPersonIsAuthenticatedAndEmailIsNew() throws Exception {
        PasswordRecoveryRequest request = new PasswordRecoveryRequest();
        request.setEmail("person@user.ru");
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request);

        mockMvc.perform(put("/api/v1/account/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(jsonPath("$.data.message").value("ok"));
    }

    @Test
    @WithUserDetails(value = "user@user.ru")
    public void shouldReturnBadRequestWhenPersonIsAuthenticatedAndEmailIsOld() throws Exception {
        PasswordRecoveryRequest request = new PasswordRecoveryRequest();
        request.setEmail("user@user.ru");
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request);

        mockMvc.perform(put("/api/v1/account/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(authenticated())
                .andExpect(jsonPath("$.error").value("invalid_request"))
                .andExpect(jsonPath("$.error_description").value("Такой email уже зарегистрирован в сети"));
    }

    @Test
    public void shouldReturnForbiddenWhenUnauthenticatedBySettingEmail() throws Exception {
        PasswordRecoveryRequest request = new PasswordRecoveryRequest();
        request.setEmail("bobby@user.ru");
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request);

        mockMvc.perform(put("/api/v1/account/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

}
