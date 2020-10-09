package main.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Data;

@Data
public class MeProfileRequest {

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("birth_date")
  private Date birthDate;

  private String phone;

  @JsonProperty("photo_id")
  private String photoURL;

  private String about;

  private int city;
  private int country;


}
