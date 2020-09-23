package main.model;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface PersonAndPostView {

  int getId();
  String getFirstName();
  String getLastName();
  Instant getRegDate();
  Date getBirthDate() ;
  String getEmail() ;
  String getPasswordHash();
  String getPhotoURL() ;
  String getAbout() ;
  String getTown() ;
  String getConfirmationCode() ;
  boolean isApproved() ;
  MessagesPermission getMessagesPermission() ;
  Instant getLastOnlineTime();
  boolean isBlocked() ;

  List<Post> getPost();

  int getPostLikes();

  List<PostComment>getPostComment();


  }




