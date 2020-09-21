package main.repository;

import main.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City, Integer> {
    Page<City> findByCountryId(int countryId, Pageable pageable);
    Page<City> findByCountryIdAndTitleIgnoreCaseContaining(int countryId, String title, Pageable pageable);
}
