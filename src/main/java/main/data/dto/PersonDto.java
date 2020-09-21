package main.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class PersonDto {
    private int Id;
    @JsonProperty(value = "first_name")
    private String firstName;
    @JsonProperty(value = "last_name")
    private String lastName;
    @JsonProperty(value = "reg_date")
    private long regDate;
    @JsonProperty(value = "birth_date")
    private long birthDate;
    private String email;
    private String phone;
    private String photo;
    private String about;
    private City city;
    private Country country;
    @JsonProperty(value = "messages_permission")
    private String messagesPermission;
    @JsonProperty(value = "last_online_time")
    private long lastOnlineTime;
    @JsonProperty(value = "is_blocked")
    private boolean blocked;

    public boolean isBlocked() {
        return blocked;
    }

    public void setIsBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public PersonDto() {
        city = new City();
        country = new Country();
    }

    public String getMessagesPermission() {
        return messagesPermission;
    }

    public void setMessagesPermission(String messagesPermission) {
        this.messagesPermission = messagesPermission;
    }

    public long getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(long lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getRegDate() {
        return regDate;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    private class City {
        private final int id = 1;
        private final String title = "Москва";

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public City(){

        }
    }

    private class Country{
        private final int id = 1;
        private final String title = "Россия";

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public Country(){

        }
    }

}
