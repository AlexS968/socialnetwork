package main.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "person")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "reg_date", nullable = false)
  private Instant regDate;

  @Column(name = "birth_date")
  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @Column(name = "e_mail", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String passwordHash;

  @Column(name = "phone")
  private String phone;

  @Column(name = "photo", columnDefinition = "text")
  private String photoURL;

  @Column(columnDefinition = "text")
  private String about;

  @ManyToOne
  @JoinColumn(name = "country_id", referencedColumnName = "id")
  private Country country;

  @ManyToOne
  @JoinColumn(name = "city_id", referencedColumnName = "id")
  private City city;

  @Column(name = "confirmation_code")
  private String confirmationCode;

  @Column(name = "is_approved", nullable = false, columnDefinition = "TINYINT")
  private boolean isApproved = false;

  @Enumerated(EnumType.STRING)
  @Column(name = "messages_permission", columnDefinition = "enum('ALL', 'FRIENDS')", nullable = false)
  private MessagesPermission messagesPermission;

  @Column(name = "last_online_time")
  private Instant lastOnlineTime;

  @Column(name = "is_blocked", nullable = false, columnDefinition = "TINYINT")
  private boolean isBlocked = false;

  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  @CollectionTable(name = "notification_settings",
      joinColumns = {@JoinColumn(name = "person_id", referencedColumnName = "id")})
  @MapKeyColumn(name = "notification_type_code")
  @MapKeyEnumerated(EnumType.STRING)
  @Column(name = "is_enabled") // value

  private Map<NotificationTypeCode, Boolean> notificationSettings;

  @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
  private List<BlockHistory> blockHistory;

  @JsonBackReference
  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
  private List<Post> posts;

  @JsonBackReference
  @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
  private List<Notification> notifications;

  @JsonBackReference
  @OneToMany(mappedBy = "dst", fetch = FetchType.LAZY)
  private Set<Friendship> requestFr;

  @JsonBackReference
  @OneToMany(mappedBy = "src", fetch = FetchType.LAZY)
  private Set<Friendship> sendFr;
}
