package main.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.data.dto.PersonDto;
import main.data.request.UserRequest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class ProfileResponse {

    private String error;
    private long timestamp;
    @JsonProperty(value = "data")
    private PersonDto personDto;

    public ProfileResponse(){
        error = "string";
        timestamp = new Date().getTime();
        personDto = new PersonDto();
        personDto.setId(1);
        personDto.setFirstName("Петр");
        personDto.setLastName("Петрович");
        personDto.setRegDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        personDto.setBirthDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        personDto.setEmail("dsf@dfsa.ru");
        personDto.setPhone("815323213");
        personDto.setPhoto("https://..../dfas.jpg");
        personDto.setAbout("Посадил дерево");
        personDto.setMessagesPermission("ALL");
        personDto.setLastOnlineTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        personDto.setIsBlocked(false);

    }

    public ProfileResponse(UserRequest userRequest) {
        error = "string";
        timestamp = new Date().getTime();
        personDto = new PersonDto();
        personDto.setId(1);
        personDto.setFirstName("Петр");
        personDto.setLastName("Петрович");
        personDto.setRegDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        personDto.setBirthDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        personDto.setEmail(userRequest.getEmail());
        personDto.setPhone("815323213");
        personDto.setPhoto("https://..../dfas.jpg");
        personDto.setAbout("Посадил дерево");
        personDto.setMessagesPermission("ALL");
        personDto.setLastOnlineTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        personDto.setIsBlocked(false);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public PersonDto getPersonDto() {
        return personDto;
    }

    public void setPersonDto(PersonDto personDto) {
        this.personDto = personDto;
    }


}
