package main.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.response.ListPersonResponse;
import main.data.response.ListPostsResponse;
import main.model.City;
import main.model.Country;
import main.model.Person;
import main.model.Post;
import main.repository.CityRepository;
import main.repository.CountryRepository;
import main.repository.PersonRepository;
import main.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SearchService {

  private final PersonRepository personRepository;
  private final CountryRepository countryRepository;
  private final CityRepository cityRepository;
  private final PostRepository postRepository;

  public ListPersonResponse searchPerson(String firstName, String lastName, Integer ageFrom,
      Integer ageTo, String country,
      String city, Integer offset, Integer itemPerPage) {

    Pageable pageable;

    if (offset == 0 && itemPerPage == 0) {
      pageable = Pageable.unpaged();
    } else {
      pageable = new OffsetPageRequest(offset, itemPerPage, Sort.unsorted());
    }

    List<Person> searchResult = new ArrayList<>();

    Page<Person> resultPage = new PageImpl<>(new ArrayList<>(), pageable,
        0); // чтобы вернуть пустую страницу если какоето условие не соблюдено

    Integer countryId = null;
    Integer cityId = null;
    Date ageFromToDate = null;
    Date ageToToDate = null;

    if (country != null) {

      Optional<Country> countryOptional = countryRepository.findByTitle(country);

      if (countryOptional.isEmpty()) {

        return new ListPersonResponse(resultPage);
      }
      countryId = countryOptional.get().getId();
    }
    if (city != null) {
      Optional<City> cityOptional = cityRepository.findByTitle(city);

      if (cityOptional.isEmpty()) {
        return new ListPersonResponse(resultPage);
      }

      cityId = cityOptional.get().getId();
    }
    if (ageFrom != null) {
      ageFromToDate = calculateBirthDateFromAge(ageFrom);
    }
    if (ageTo != null) {
      ageToToDate = calculateBirthDateFromAge(ageTo);
    }

    List<Optional<Person>> res = personRepository
        .findPersonByNameLastNameAgeCityCountry(firstName, lastName, ageFromToDate, ageToToDate,
            countryId, cityId);

    if (!res.isEmpty()) {
      res.stream().forEach(r -> searchResult.add(r.get()));
    }

    resultPage = new PageImpl<>(searchResult, pageable, searchResult.size());

    return new ListPersonResponse(resultPage);

  }

  public ListPostsResponse searchPost(String text, Long dateFrom, Long dateTo, String author,
      Integer offset, Integer itemPerPage) {

    Pageable pageable;

    if (offset == 0 && itemPerPage == 0) {
      pageable = Pageable.unpaged();
    } else {
      pageable = new OffsetPageRequest(offset, itemPerPage, Sort.unsorted());
    }

    List<Post> searchPostResult = new ArrayList<>();

    Page<Post> resultPostPage = new PageImpl<>(new ArrayList<>(), pageable, 0);

    Date from = null;
    Date to = null;
    Set<Integer> authorsIds = new HashSet<>();
    String textUpdated = "%" + text + "%";

    if (dateFrom != null && dateTo != null) {
      from = new Date(dateFrom);
      to = new Date(dateTo);
    }
    if (author != null) {
      List<Optional<Person>> authors = personRepository
          .findByLastNameLikeOrFirstNameLike(author, author);

      if (authors.isEmpty()) {
        return new ListPostsResponse(resultPostPage);
      }

      Set<Integer> authorsIdsTemp = new HashSet<>();

      authors.stream().forEach(a -> authorsIdsTemp.add(a.get().getId()));

      authorsIds = authorsIdsTemp;

    }

    List<Optional<Post>> res = postRepository
        .findByTextPeriodAuthor(textUpdated, from, to, authorsIds);

    if (!res.isEmpty()) {
      res.stream().forEach(r -> searchPostResult.add(r.get()));
    }

    resultPostPage = new PageImpl<>(searchPostResult, pageable, searchPostResult.size());

    return new ListPostsResponse(resultPostPage);
  }

  private Date calculateBirthDateFromAge(int age) {

    LocalDate today = LocalDate.now();

    LocalDate from = today.minusYears(age);

    return Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

  }


}
