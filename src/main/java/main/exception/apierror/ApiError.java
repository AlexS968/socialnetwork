package main.exception.apierror;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ApiError {
    String error;
    @JsonProperty("error_description")
    String errorDescription;
    String statusText;

    public ApiError(String error, String errorDescription){
        this.error = error;
        this.errorDescription = errorDescription;
        statusText = errorDescription;
    }
}
