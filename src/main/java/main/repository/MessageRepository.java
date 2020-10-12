package main.repository;

import main.model.Message;
import main.model.ReadStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    Page<Message> findByDialog_id(int dialogId, Pageable pageable);
    long countByReadStatusAndAuthor_idNotAndDialog_id(ReadStatus readStatus, int filterAuthorId, int dialogId);
    Message findById(int messageId);
    long countByReadStatusAndAuthor_idNotAndDialog_persons_id(ReadStatus readStatus, int filterAuthorId, int personId);
    Message findTopByDialog_idOrderByTimeDesc(int dialogId);
}

