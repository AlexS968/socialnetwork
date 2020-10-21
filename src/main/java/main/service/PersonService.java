package main.service;

import main.data.request.LoginRequest;
import main.data.request.MeProfileRequest;
import main.data.response.base.Response;
import main.data.response.type.*;
import main.model.Person;

public interface PersonService {
    Response<PersonInLogin> login(LoginRequest request);
    Response<ResponseMessage> logout();
    Response<MeProfile> getMe();
    Response<MeProfileUpdate> putMe(MeProfileRequest updatedCurrentPerson);
    Response<InfoInResponse> deleteMe();
    Person getCurrentPerson();
    Person getById(int personId);
    Person save(Person person);
}
