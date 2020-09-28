package main.repository;

import main.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByEmail(String email);
    Person findById(int id);
    Person findByConfirmationCode(String confirmationCode);
}
