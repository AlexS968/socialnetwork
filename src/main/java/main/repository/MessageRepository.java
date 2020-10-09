package main.repository;

import main.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    Page<Message> findByDialog_id(int dialogId, Pageable pageable);
    Message findById(int messageId);
}

