package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Storage {


//        "id": "string",
//        "ownerId": 12,
//        "fileName": "string",
//        "relativeFilePath": "string",
//        "rawFileURL": "string",
//        "fileFormat": "string",
//        "bytes": 0,
//        "fileType": "IMAGE",
//        "createdAt": 0


  @JsonProperty("id")
  private String id; // просто имя фото?
  private int ownerId;
  private String fileName;
  private String relativeFilePath;
  private String rawFileURL;
  private String fileFormat;  // jpg
  private long bytes;
  private String fileType;    // "IMAGE"
  private long createdAt;

 // public Storage() {}


}
