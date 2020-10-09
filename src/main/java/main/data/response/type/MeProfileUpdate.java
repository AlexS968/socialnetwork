package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.MessagesPermission;
import main.model.Person;
@Data
public class MeProfileUpdate {

 @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("birth_date")
  private long birthDate;

  private String phone;

  private String photo;

  private String about;

  @JsonProperty("messages_permission")
  private MessagesPermission messagesPermission;

  private CityList city;

  private CountryList country;

  private int id;

  @JsonProperty("reg_date")
  private long regDate;

  private String email;

  @JsonProperty("last_online_time")
  private long lastOnlineTime;

  @JsonProperty("is_blocked")
  private boolean isBlocked;


  public MeProfileUpdate(Person person) {
    this.firstName = person.getFirstName();
    this.lastName = person.getLastName();
    this.birthDate = person.getBirthDate().getTime();
    this.phone = person.getPhone();
    this.photo = person.getPhotoURL();
    this.about = person.getAbout();
    this.city = new CityList(person.getCity());
    this.country = new CountryList(person.getCountry());
    this.messagesPermission = person.getMessagesPermission();

    this.email = person.getEmail();
    this.id = person.getId();
    this.lastOnlineTime = person.getLastOnlineTime() != null ? person.getLastOnlineTime().toEpochMilli() : 0;
    this.regDate = person.getRegDate() != null ? person.getRegDate().toEpochMilli() : 0;
    this.isBlocked = person.isBlocked();


  }

}
