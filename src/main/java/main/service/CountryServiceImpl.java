package main.service;

import lombok.AllArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.request.ListCountryRequest;
import main.data.response.ListCountryResponse;
import main.data.response.type.CountryInCountryList;
import main.model.Country;
import main.repository.CountryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public ListCountryResponse list(ListCountryRequest request) {
        List<CountryInCountryList> countries = new ArrayList<>();

        Pageable pageable;
        Page<Country> page;

        if (request.getItemPerPage() > 0) {
            pageable = new OffsetPageRequest(request.getOffset(), request.getItemPerPage(), Sort.unsorted());
        } else {
            pageable = Pageable.unpaged();
        }

        if (request.getCountry() != null && !request.getCountry().isEmpty()) {
            page = countryRepository.findByTitleIgnoreCaseContaining(request.getCountry(), pageable);
        } else {
            page = countryRepository.findAll(pageable);
        }

        page.forEach(i -> {
            CountryInCountryList item = new CountryInCountryList(i);
            countries.add(item);
        });

        ListCountryResponse listCountryResponse = new ListCountryResponse(countries);
        listCountryResponse.setTotal(page.getTotalElements());
        listCountryResponse.setOffset(request.getOffset());
        listCountryResponse.setPerPage(request.getItemPerPage());

        return listCountryResponse;
    }
}
