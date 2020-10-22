package main.service;

import lombok.AllArgsConstructor;
import main.data.response.CaptchaResponse;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.Objects;

@AllArgsConstructor
@Service
public class CaptchaServiceImpl implements CaptchaService{


    @Override
    public CaptchaResponse checkCaptcha(String token, String secretCode, String captchaUrl) {
        JSONParser parser = new JSONParser();
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();
        try {
            String url = String.format(captchaUrl, secretCode, ((JSONObject) parser.parse(token)).get("token").toString());
            CaptchaResponse response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);
            if (!Objects.requireNonNull(response).isSuccess()) {
                throw  new BadRequestException(new ApiError("invalid_request", "Bad request"));
            } else {
                return response;
            }
        }catch (ParseException  parseException){
            throw new BadRequestException(new ApiError("invalid_request", "Bad request"));
        }
    }
}
