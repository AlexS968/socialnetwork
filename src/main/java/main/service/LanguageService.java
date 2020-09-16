package main.service;

import main.data.request.ListLanguageRequest;
import main.data.response.ListLanguageResponse;

public interface LanguageService {
    ListLanguageResponse list(ListLanguageRequest request);
}
