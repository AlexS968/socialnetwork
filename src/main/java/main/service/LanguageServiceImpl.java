package main.service;

import lombok.AllArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.request.ListLanguageRequest;
import main.data.response.ListLanguageResponse;
import main.data.response.type.LanguageInLanguageList;
import main.model.Language;
import main.repository.LanguageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;

    @Override
    public ListLanguageResponse list(ListLanguageRequest request) {
        List<LanguageInLanguageList> languages = new ArrayList<>();

        Pageable pageable;
        Page<Language> page;

        if (request.getItemPerPage() > 0) {
            pageable = new OffsetPageRequest(request.getOffset(), request.getItemPerPage(), Sort.unsorted());
        } else {
            pageable = Pageable.unpaged();
        }

        if (request.getLanguage() != null && !request.getLanguage().isEmpty()) {
            page = languageRepository.findByTitleIgnoreCaseContaining(request.getLanguage(), pageable);
        } else {
            page = languageRepository.findAll(pageable);
        }

        page.forEach(i -> {
            LanguageInLanguageList item = new LanguageInLanguageList(i);
            languages.add(item);
        });

        ListLanguageResponse listLanguageResponse = new ListLanguageResponse(languages);
        listLanguageResponse.setTotal(page.getTotalElements());
        listLanguageResponse.setOffset(request.getOffset());
        listLanguageResponse.setPerPage(request.getItemPerPage());

        return listLanguageResponse;
    }
}
