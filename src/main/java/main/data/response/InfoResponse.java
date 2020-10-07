package main.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.data.response.base.RecordResponse;
import main.data.response.type.InfoInResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoResponse extends RecordResponse {

  private InfoInResponse data;
}
