package main.repository;

import main.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByEmail(String email);
    Person findById(int id);
    Optional<Person> findByConfirmationCode(String confirmationCode);
}
