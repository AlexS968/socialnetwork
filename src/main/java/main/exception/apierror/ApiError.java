package main.exception.apierror;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    String error;
    @JsonProperty("error_description")
    String errorDescription;
}
