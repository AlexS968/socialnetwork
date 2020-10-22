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
import main.data.response.base.ListResponse;
import main.data.response.type.CommentInResponse;
import main.data.response.type.PersonInPersonList;
import main.data.response.type.PostInResponse;
import main.model.City;
import main.model.Country;
import main.model.Person;
import main.model.Post;
import main.model.Tag;
import main.repository.CityRepository;
import main.repository.CountryRepository;
import main.repository.PersonRepository;
import main.repository.PostCommentRepository;
import main.repository.PostRepository;
import main.repository.TagRepository;
import org.springframework.data.domain.Page;
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
  private final PostCommentRepository postCommentRepository;
  private final CommentService commentService;
  private final TagRepository tagRepository;

  public ListResponse<PersonInPersonList> searchPerson(String firstName, String lastName,
      Integer ageFrom,
      Integer ageTo, String country,
      String city, Integer offset, Integer itemPerPage) {

    Pageable pageable;

    if (offset == 0 && itemPerPage == 0) {
      pageable = Pageable.unpaged();
    } else {
      pageable = new OffsetPageRequest(offset, itemPerPage, Sort.unsorted());
    }

    List<PersonInPersonList> searchResult = new ArrayList<>();

    Page<Person> resultPage;

    Integer countryId = null;
    Set<Integer> cityIds = new HashSet<>();
    Date ageFromToDate = null;
    Date ageToToDate = null;

    if (country != null) {

      Optional<Country> countryOptional = countryRepository.findByTitle(country);

      if (countryOptional.isEmpty()) {

        return new ListResponse<>(searchResult, 0,
            offset,
            itemPerPage);
      }
      countryId = countryOptional.get().getId();
    }
    if (city != null) {

      //города в мире
      if (country == null) {

        List<Optional<City>> cityOptional = cityRepository.findByTitle(city);

        if (cityOptional.isEmpty()) {
          return new ListResponse<>(searchResult, 0,
              offset,
              itemPerPage);
        }

        cityOptional.stream().forEach(c -> cityIds.add(c.get().getId()));
      }
      //города в стране
      else {

        List<Optional<City>> cityOptional = cityRepository.findByTitleAndCountryId(city, countryId);

        if (cityOptional.isEmpty()) {
          return new ListResponse<>(searchResult, 0,
              offset,
              itemPerPage);
        }

        cityOptional.stream().forEach(c -> cityIds.add(c.get().getId()));

      }


    }
    if (ageFrom != null) {
      ageFromToDate = calculateBirthDateFromAge(ageFrom);
    }
    if (ageTo != null) {
      ageToToDate = calculateBirthDateFromAge(ageTo);
    }

    resultPage = personRepository
        .findPersonByNameLastNameAgeCityCountry(firstName, lastName, ageFromToDate, ageToToDate,
            countryId, cityIds, pageable);

    resultPage.forEach(r -> searchResult.add(new PersonInPersonList(r)));

    return new ListResponse<>(searchResult, resultPage.getTotalElements(),
        offset,
        itemPerPage);

  }

  public ListResponse<PostInResponse> searchPost(String text, Long dateFrom, Long dateTo,
      String author, List<String> tags,
      Integer offset, Integer itemPerPage) {

    Pageable pageable;

    if (offset == 0 && itemPerPage == 0) {
      pageable = Pageable.unpaged();
    } else {
      pageable = new OffsetPageRequest(offset, itemPerPage, Sort.unsorted());
    }

    List<PostInResponse> searchPostResult = new ArrayList<>();

    Page<Post> resultPostPage;
    Date from = null;
    Date to = null;
    Set<Integer> authorsIds = new HashSet<>();
    String textUpdated = "%" + text + "%";
    Set<Integer> tagsIds = new HashSet<>();

    if (dateFrom != null && dateTo != null) {
      from = new Date(dateFrom);
      to = new Date(dateTo);
    }
    if (author != null) {
      List<Optional<Person>> authors = personRepository
          .findByLastNameLikeOrFirstNameLike(author, author);

      if (authors.isEmpty()) {
        return new ListResponse<>(searchPostResult, 0, offset, itemPerPage);
      }

      Set<Integer> authorsIdsTemp = new HashSet<>();

      authors.stream().forEach(a -> authorsIdsTemp.add(a.get().getId()));

      authorsIds = authorsIdsTemp;

    }
    if (tags.size() > 0) {

      List<Optional<Tag>> tagsFound = tagRepository.findTagsByTagNames(tags);

      if (tagsFound.isEmpty()) {
        return new ListResponse<>(searchPostResult, 0, offset, itemPerPage);
      }

      tagsFound.forEach(t -> tagsIds.add(t.get().getId()));

    }

    resultPostPage = postRepository
        .findByTextPeriodAuthor(textUpdated, from, to, authorsIds, tagsIds, pageable);

    List<CommentInResponse> comments = commentService.getCommentsList(resultPostPage.getContent());
    resultPostPage
        .forEach(p -> searchPostResult.add(new PostInResponse(p, comments, -1))); //TODO check necessity

    return new ListResponse<>(searchPostResult, resultPostPage.getTotalElements(), offset,
        itemPerPage);
  }

  private Date calculateBirthDateFromAge(int age) {

    LocalDate today = LocalDate.now();

    LocalDate from = today.minusYears(age);

    return Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());

  }


}
