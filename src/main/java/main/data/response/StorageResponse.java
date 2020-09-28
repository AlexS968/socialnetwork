package main.data.response;

import lombok.Data;
import main.data.response.base.RecordResponse;
import main.data.response.type.Storage;

@Data
public class StorageResponse extends RecordResponse {

  private Storage data;

}
