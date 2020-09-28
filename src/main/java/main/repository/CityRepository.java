package main.repository;

import main.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City, Integer> {
    City findById(int cityId);
    Page<City> findByCountryId(int countryId, Pageable pageable);
    Page<City> findByCountryIdAndTitleIgnoreCaseContaining(int countryId, String title, Pageable pageable);
    @Query(nativeQuery = true, value = "select * from city where city.title = :cityTitle and country_id = :countryId")
    City findByCityTitleAndCountryId(String cityTitle, int countryId);
}
