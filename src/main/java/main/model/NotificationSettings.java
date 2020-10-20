package main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "notification_settings")
@Data
public class NotificationSettings {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Person personId;

  @Column(name = "is_enabled", nullable = false, columnDefinition = "TINYINT")
  private boolean isEnabled = true;

  // NotificationType ------ заменить!!!

  @Enumerated(EnumType.STRING)
  @Column(name = "notification_type_code", columnDefinition = "enum('COMMENT_COMMENT', 'POST_COMMENT', 'FRIEND_REQUEST', 'MESSAGE', 'FRIEND_BIRTHDAY')", nullable = false)
  private NotificationTypeCode notificationTypeCode;


}
