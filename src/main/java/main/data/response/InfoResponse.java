package main.data.response;

import lombok.Data;
import main.data.response.base.RecordResponse;
import main.data.response.type.InfoInResponse;

@Data
public class InfoResponse extends RecordResponse {

  private InfoInResponse data;
}
