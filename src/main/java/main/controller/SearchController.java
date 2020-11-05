package main.controller;

import lombok.AllArgsConstructor;
import main.data.response.base.ListResponse;
import main.data.response.type.MeProfile;
import main.data.response.type.PostInResponse;
import main.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/v1/users/search")
    public ResponseEntity<ListResponse<MeProfile>> searchPerson(
            @RequestParam(required = false, name = "first_name") String firstName,
            @RequestParam(required = false, name = "last_name") String lastName,
            @RequestParam(required = false, name = "age_from") Integer ageFrom,
            @RequestParam(required = false, name = "age_to") Integer ageTo,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer itemPerPage

    ) {

        return ResponseEntity.ok(searchService
                .searchPerson(firstName, lastName, ageFrom, ageTo, country, city, offset, itemPerPage));
    }

    @GetMapping("/api/v1/post")
    public ResponseEntity<ListResponse<PostInResponse>> searchNews(
            @RequestParam(required = false, name = "text") String text,
            @RequestParam(required = false, name = "date_from") Long dateFrom,
            @RequestParam(required = false, name = "date_to") Long dateTo,
            @RequestParam(required = false, name = "author") String author,
            @RequestParam(required = false, name = "tags") List<String> tags,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer itemPerPage) {

        return ResponseEntity.ok(searchService.searchPost(text, dateFrom, dateTo, author, tags,
                offset, itemPerPage));
    }


}
