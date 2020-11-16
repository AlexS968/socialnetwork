package main.exception.apierror;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ApiError")
@Data
public class ApiError {

    @ApiModelProperty(value = "error", example = "invalid_request")
    String error;
    @ApiModelProperty(value = "error_description", example = "string")
    @JsonProperty("error_description")
    String errorDescription;
    @ApiModelProperty(value = "status_text", example = "401")
    String statusText;

    public ApiError(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
        statusText = errorDescription;
    }
}
