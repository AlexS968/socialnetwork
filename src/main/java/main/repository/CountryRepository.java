package main.repository;

import main.model.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends PagingAndSortingRepository<Country, Integer> {
    Page<Country> findAll(Pageable pageable);
    Page<Country> findByTitleIgnoreCaseContaining(String title, Pageable pageable);
}
