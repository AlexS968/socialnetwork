package main.data.response;

import lombok.Data;
import main.data.response.base.RecordResponse;
import main.data.response.type.MeProfile;

@Data
public class MeProfileResponse extends RecordResponse {

  private MeProfile data;

}
