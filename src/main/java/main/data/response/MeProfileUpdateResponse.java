package main.data.response;

import lombok.Data;
import main.data.response.base.RecordResponse;
import main.data.response.type.MeProfileUpdate;

@Data
public class MeProfileUpdateResponse extends RecordResponse {
  private MeProfileUpdate data;


}