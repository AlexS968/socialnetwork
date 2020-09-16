package main.repository;

import main.model.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends PagingAndSortingRepository<Language, Integer> {
    Page<Language> findAll(Pageable pageable);
}

