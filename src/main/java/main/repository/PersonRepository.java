package main.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import main.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

  Person findByEmail(String email);

  Person findById(int id);

  Optional<Person> findByConfirmationCode(String confirmationCode);

  List<Optional<Person>> findByCityId(Integer cityId);

  List<Optional<Person>> findByLastNameLikeOrFirstNameLike(String lastName, String firstName);

  @Query(nativeQuery = true, value = "SELECT * FROM person "
      + "WHERE (:firstName is null or first_name = :firstName) and (:lastName is null or last_name = :lastName) and "
      + "(:ageFrom is null or birth_date <= :ageFrom) and (:ageTo is null or birth_date >= :ageTo) and "
      + "(:countryId is null or country_id = :countryId) and (:cityId is null or city_id = :cityId)")
  List<Optional<Person>> findPersonByNameLastNameAgeCityCountry(
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("ageFrom") Date ageMore,
      @Param("ageTo") Date ageLess,
      @Param("countryId") Integer countryId,
      @Param("cityId") Integer cityId);
}
