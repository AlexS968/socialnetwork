package main.repository;

import main.model.Notification;
import main.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    Page<Notification> findAll(Pageable pageable);

    Page<Notification> findByReceiver(Person person,Pageable pageable);
}
