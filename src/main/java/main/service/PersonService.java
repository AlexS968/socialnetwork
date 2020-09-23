package main.service;

import java.time.Instant;
import java.util.Optional;
import main.core.OffsetPageRequest;
import main.data.PersonPrincipal;
import main.model.FriendshipStatus;
import main.model.FriendshipStatusCode;
import main.model.Person;
import main.model.PersonAndCityCountryView;
import main.model.PersonAndPostView;
import main.model.Post;
import main.repository.FriendshipRepository;
import main.repository.FriendshipStatusRepository;
import main.repository.PersonRepository;
import main.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  @Autowired
  PersonRepository personRepository;
  @Autowired
  FriendshipRepository friendshipRepository;
  @Autowired
  FriendshipStatusRepository friendshipStatusRepository;
  @Autowired
  PostRepository postRepository;

  public ResponseEntity getCurrentUser() {

    Person currentPerson = getCurrentUserFromAuthentication();
    int id = currentPerson.getId();
    PersonAndCityCountryView detailed = personRepository.getDetailedPerson(id);

    //-------------
    System.out.println(" ---------DETAILED--------------");

    System.out.println(detailed.getAbout() + " " + detailed.getEmail() + " " + detailed.getLastName());
    System.out.println(detailed.getFirstName()+ " " + detailed.getCity().getTitle());


    return new ResponseEntity(detailed, HttpStatus.OK);

  }

  public ResponseEntity updateCurrentUser(Person updatedCurrentUser) {



// нужен PersonUpdate???? поля
//    about:
//    birth_date:
//    city:
//    country:
//    first_name:
//    last_na
//    photo_id:

    Integer id = updatedCurrentUser.getId();

    Optional<Person> personOptional = personRepository.findById(id);
    if (!personOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    personRepository.save(updatedCurrentUser);

    return new ResponseEntity(personRepository.getDetailedPerson(id),
        HttpStatus.OK);

  }

  public ResponseEntity deleteCurrentUser() {
    personRepository.deleteById(getCurrentUserFromAuthentication().getId());
    return new ResponseEntity(HttpStatus.OK);
  }

  public ResponseEntity getUserById(Integer id) {

    Optional<Person> personOptional = personRepository.findById(id);
    if (!personOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return new ResponseEntity(personOptional.get(), HttpStatus.OK);

  }

  public ResponseEntity getUserWall(Integer id, Integer offset, Integer itemPerPage) {

    OffsetPageRequest pageable = new OffsetPageRequest(offset, itemPerPage, Sort.by("postId"));

    Page<PersonAndPostView> postsDetailed = postRepository.getPostsByAuthorId(id, pageable);

    return new ResponseEntity(postsDetailed, HttpStatus.OK);
  }

  public ResponseEntity postOnCurrentUserWall(Integer id, Instant publishDate, Post post) {

    Post postToAdd = post;
    Person author = personRepository.findById(id).get();
    postToAdd.setAuthor(author);
    if (publishDate != null) {
      postToAdd.setTime(publishDate);
    } else {
      postToAdd.setTime(Instant.now());
    }
    postRepository.save(postToAdd);

    PersonAndPostView responsePerson = personRepository
        .getPersonAndAddedPost(id, postToAdd.getId());
    return new ResponseEntity(responsePerson, HttpStatus.OK);
  }

  public ResponseEntity search(String firstName, String lastName,
      Integer ageFro, Integer ageTo, Integer countryId, Integer cityId, Integer offset,
      Integer itemPerPage) {

    OffsetPageRequest pageable = new OffsetPageRequest(offset, itemPerPage, Sort.by("postId"));

    Page<PersonAndCityCountryView> searchResult = personRepository
        .findUserByAnyFieldMatch(firstName, lastName,
            ageFro, ageTo, countryId, cityId, pageable);

    return new ResponseEntity(searchResult,
        HttpStatus.OK);
  }

  public ResponseEntity blockUser(Integer id) {

    // можно заблокировать только друга??????

    FriendshipStatus friendshipStatus = getFriendshipStatusByIds(id);
    friendshipStatus.setCode(FriendshipStatusCode.BLOCKED);
    friendshipStatusRepository.save(friendshipStatus);

    return new ResponseEntity(HttpStatus.OK);
  }

  public ResponseEntity deleteBlockUser(Integer id) {

    // можно заблокировать только друга??????

    FriendshipStatus friendshipStatus = getFriendshipStatusByIds(id);
    friendshipStatus.setCode(FriendshipStatusCode.FRIEND);
    friendshipStatusRepository.save(friendshipStatus);

    return new ResponseEntity(HttpStatus.OK);
  }


  private Person getCurrentUserFromAuthentication() {

    return ((PersonPrincipal) SecurityContextHolder.getContext().
       getAuthentication().getPrincipal()).getPerson();

    // return personRepository.findByEmail("vv@mail.ru");
  }

  private FriendshipStatus getFriendshipStatusByIds(Integer id) {

    Integer currentPersonId = getCurrentUserFromAuthentication().getId();
    Integer personToBlockId = id;

    Integer friendshipStatusId = friendshipRepository
        .findFriendshipByPersonsId(currentPersonId, personToBlockId).getStatus().getId();
    FriendshipStatus friendshipStatus = friendshipStatusRepository.findById(friendshipStatusId)
        .get();

    return friendshipStatus;
  }


}
