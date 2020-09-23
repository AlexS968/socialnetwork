package main.model;

import java.time.Instant;
import java.util.Date;

public interface PersonAndCityCountryView {

   int getId();
   String getFirstName();
   String getLastName();
   Instant getRegDate();
   Date getBirthDate() ;
   String getEmail() ;
   String getPhone() ;
   String getPhotoURL() ;
   String getAbout() ;
   City getCity();
   Country getCountry();
   MessagesPermission getMessagesPermission() ;
   Instant getLastOnlineTime();






}
