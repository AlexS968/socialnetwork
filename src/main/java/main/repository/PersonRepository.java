package main.repository;

import main.model.Person;
import main.model.PersonAndCityCountryView;
import main.model.PersonAndPostView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

  String queryForAgeParam = "CASE "
      + "WHEN p.ageFrom >= 0 and p.ageTo >= 0 THEN 'or (p.ageFrom >= :ageFrom and p.ageTo =< :ageTo)' "
      + "WHEN p.ageFrom >= 0 and p.ageTo = NULL THEN 'or (p.ageFrom >= :ageFrom)' "
      + "WHEN p.ageFrom = NULL and p.ageTo >= 0 THEN 'or (p.ageTo =< :ageTo)' "
      // + "WHEN p.ageFrom = NULL and p.ageTo = NULL THEN 'or (ageTo is NULL and ageFrom is NULL)'" //??
      + "END";

  //---SQL

@Query(nativeQuery = true, value =
    "SELECT Person.id as id, Person.first_name as first_name, Person.last_name as last_name, Person.reg_date as reg_date,  "
        + "Person.birth_date as birth_date, Person.e_mail as email, Person.phone as phone, Person.photo as photo, Person.about as about, "
        + "City.id as city_id, City.title as city_title, Country.id as country_id, Country.title as country_title, "
        + "Person.messages_permission as messages_permission, Person.last_online_time as last_online_time "
        + "FROM Person "
        + "JOIN Country ON Person.country_id = Country.id "
        + "JOIN City  ON Person.city_id = City.id "
        + " WHERE Person.id = :id")
PersonAndCityCountryView getDetailedPerson( @Param("id")Integer id);

// JPQL прил не запускается без nativeQuery = true

  @Query(nativeQuery = true, value = "SELECT p, country, city FROM Person p "
      + "JOIN Country country ON p.countryId = country.id "
      + "JOIN City city ON p.cityId = city.id "
      + " WHERE (:firstName is null or p.firstName = :firstName) or (:lastName is null or p.lastName = :lastName) "
      + queryForAgeParam
      + " or (:countryId is null or p.countryId = :countryId) or (:cityId is null or p.cityId = :cityId or ")
  Page<PersonAndCityCountryView> findUserByAnyFieldMatch(
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("ageFrom") Integer ageFrom,
      @Param("ageTo") Integer ageTo,
      @Param("countryId") Integer countryId,
      @Param("cityId") Integer cityId,
      Pageable pageable);


  @Query(nativeQuery = true, value =
      "SELECT person, post, postComment, count(postLike) as postLike, country, city FROM Person person "
          + "JOIN Post post ON person.id = post.authorId "
          + "JOIN PostComment postComment ON post.id = postComment.postId "
          + "JOIN PostLike postLike ON post.id = postLike.postId "
          + "JOIN Country country ON person.countryId = country.id "
          + "JOIN City city ON person.cityId = city.id "
          + "WHERE person.id = :personId and post.id = :postId")
  PersonAndPostView getPersonAndAddedPost(@Param("personId") Integer personId,
      @Param("postId") Integer postId);

  Person findByEmail(String email);
  Person findById(int id);




}
