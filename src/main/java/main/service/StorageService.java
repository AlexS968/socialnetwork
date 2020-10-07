package main.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;
import main.data.PersonPrincipal;
import main.data.response.StorageResponse;
import main.data.response.type.Storage;
import main.model.Person;
import main.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

  @Value("${upload.path}")
  String uploadPath;

  @Autowired
  PersonRepository personRepository;

  public StorageResponse store(MultipartFile file, String type){

//        "id": "string",
//        "ownerId": 12,
//        "fileName": "string",
//        "relativeFilePath": "string",
//        "rawFileURL": "string",
//        "fileFormat": "string",
//        "bytes": 0,
//        "fileType": "IMAGE",
//        "createdAt": 0

 // String path = resources + file.getOriginalFilename();



  String uuidFile = UUID.randomUUID().toString();
  String resultName = uuidFile + "_" + file.getOriginalFilename();

 //   String resultName = file.getOriginalFilename();

  try {
    file.transferTo(Paths.get(uploadPath + "/" + resultName));
  } catch (IOException e) {
    e.printStackTrace();
  }

  Person photoOwner = ((PersonPrincipal) SecurityContextHolder.getContext().
      getAuthentication().getPrincipal()).getPerson();
    photoOwner.setPhotoURL("/img/" + resultName);  // http://localhost:8080/img/pic.jpg  http://localhost:8080/img/ = "C:\Users\vitta\Desktop\Storage\"
    personRepository.save(photoOwner);

  Storage storage = new Storage();


 // storage.setId(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")));
  storage.setId("/img/" + resultName);
  storage.setOwnerId(photoOwner.getId());
  storage.setFileName(resultName);
  storage.setRelativeFilePath("/" + resultName);
  storage.setRawFileURL("/img/" + resultName); // Необработанный URL-адрес определяется как часть URL-адреса после сведений о домене
  storage.setFileFormat(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
  storage.setBytes(file.getSize());
  storage.setFileType(type);
  storage.setCreatedAt(new Date().getTime());




  StorageResponse response = new StorageResponse();
  response.setData(storage);



  return response;
}





}
