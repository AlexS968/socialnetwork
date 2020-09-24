package main.controller;

import main.data.request.MeProfileRequest;
import main.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class PersonController {

  @Autowired
  PersonServiceImpl personServiceImpl;

  @GetMapping("/me")
  public ResponseEntity getCurrentUser() {

    return ResponseEntity.ok(personServiceImpl.getMe());
  }

  @PutMapping("/me")
  public ResponseEntity updateCurrentUser(
      @RequestBody MeProfileRequest updatedCurrentUser) {

    return ResponseEntity.ok(personServiceImpl.putMe(updatedCurrentUser));

  }

  @DeleteMapping("/me")
  public ResponseEntity delete() {
    return ResponseEntity.ok(personServiceImpl.deleteMe());
  }

//  @GetMapping("/{id}")
//  public ResponseEntity getUserById(@PathVariable int id) {
//
//    return personService.getUserById(id);
//
//  }
//
//  @GetMapping("/{id}/wall")
//  public ResponseEntity getUserWall(
//      @PathVariable int id,
//      @RequestParam(required = false, defaultValue = "0") Integer offset,
//      @RequestParam(required = false, defaultValue = "10") Integer itemPerPage) {
//
//    return personService.getUserWall(id, offset, itemPerPage);
//  }
//
//  // publishDate и currentUserId должны быть в @RequestBody тк Post @RequiredArgsConstructor (???)
//  // но в апи-  /// или поменять/добавить конструктор Post??
//
//  @PostMapping("/{id}/wall")
//  public ResponseEntity postOnCurrentUserWall(
//      @PathVariable int currentUserId,
//      @RequestParam(required = false) Instant publishDate,
//      @RequestBody Post post) {
//
//    return personService.postOnCurrentUserWall(currentUserId, publishDate, post);
//  }
//
//  @GetMapping("/search")
//  public ResponseEntity searchUser(
//      @RequestParam(required = false) String firstName,
//      @RequestParam(required = false) String lastName,
//      @RequestParam(required = false) Integer ageFrom,
//      @RequestParam(required = false) Integer ageTo,
//      @RequestParam(required = false) Integer countryId,
//      @RequestParam(required = false) Integer cityId,
//      @RequestParam(required = false, defaultValue = "0") Integer offset,
//      @RequestParam(required = false, defaultValue = "10") Integer itemPerPage) {
//    return personService.search(firstName, lastName, ageFrom, ageTo, countryId, cityId, offset,
//        itemPerPage);
//  }
//
//  @PutMapping("/block/{id}")
//  public ResponseEntity blockUser(@PathVariable int id) {
//    return personService.blockUser(id);
//  }
//
//  @DeleteMapping("/block/{id}")
//  public ResponseEntity deleteBlockUser(@PathVariable int id) {
//    return personService.deleteBlockUser(id);
//  }

}
