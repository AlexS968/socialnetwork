package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeoLocationResponseShort {

  @JsonProperty("country_name")
  private String countryName;

  @JsonProperty("city")
  private String city;

}
