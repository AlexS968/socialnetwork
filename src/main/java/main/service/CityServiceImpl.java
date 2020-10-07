package main.service;

import lombok.AllArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.request.ListCityRequest;
import main.data.response.ListCityResponse;
import main.data.response.type.CityInCityList;
import main.model.City;
import main.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    public ListCityResponse list(ListCityRequest request) {
        List<CityInCityList> cities = new ArrayList<>();

        Pageable pageable;
        Page<City> page;

        if (request.getItemPerPage() > 0) {
            pageable = new OffsetPageRequest(request.getOffset(), request.getItemPerPage(), Sort.unsorted());
        } else {
            pageable = Pageable.unpaged();
        }

        if (request.getCity() != null && !request.getCity().isEmpty()) {
            page = cityRepository.findByCountryIdAndTitleIgnoreCaseContaining(
                    request.getCountryId(),
                    request.getCity(),
                    pageable
            );
        } else {
            page = cityRepository.findByCountryId(request.getCountryId(), pageable);
        }

        page.forEach(i -> {
            CityInCityList item = new CityInCityList(i);
            cities.add(item);
        });

        ListCityResponse listCityResponse = new ListCityResponse(cities);
        listCityResponse.setTotal(page.getTotalElements());
        listCityResponse.setOffset(request.getOffset());
        listCityResponse.setPerPage(request.getItemPerPage());

        return listCityResponse;
    }
}
