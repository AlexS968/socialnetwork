package main.repository;

import main.model.City;
import main.model.Dialog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialogRepository extends JpaRepository<Dialog, Integer> {
    Page<Dialog> findByPersons_id(int personId, Pageable pageable);
}
