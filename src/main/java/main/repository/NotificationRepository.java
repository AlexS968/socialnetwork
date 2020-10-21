package main.repository;

import main.model.Notification;
import main.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    Page<Notification> findAll(Pageable pageable);

    Page<Notification> findByReceiver(Person person, Pageable pageable);

    @Query(value = "SELECT * FROM notification " +
            "WHERE person_id = ?1 AND type_id IN ?2", nativeQuery = true)
    Page<Notification> findByReceiverAndType(int personId, List<Integer> typeId, Pageable pageable);
}
