package main.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import main.data.PersonPrincipal;
import main.data.response.StorageResponse;
import main.data.response.type.Storage;
import main.model.Person;
import main.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

  public static final String resources = "src\\main\\resources\\static\\static\\img\\user\\";

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

  String path = resources + file.getOriginalFilename();

  try {
    file.transferTo(Paths.get(path));
  } catch (IOException e) {
    e.printStackTrace();
  }

  Person photoOwner = ((PersonPrincipal) SecurityContextHolder.getContext().
      getAuthentication().getPrincipal()).getPerson();

  Storage storage = new Storage();


 // storage.setId(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")));
  storage.setId("static/img/user/" + file.getOriginalFilename());
  storage.setOwnerId(photoOwner.getId());
  storage.setFileName(file.getOriginalFilename());
  storage.setRelativeFilePath(path);
  storage.setRawFileURL("static/img/user/" + file.getOriginalFilename()); // Необработанный URL-адрес определяется как часть URL-адреса после сведений о домене
  storage.setFileFormat(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
  storage.setBytes(file.getSize());
  storage.setFileType(type);
  storage.setCreatedAt(new Date().getTime());


  StorageResponse response = new StorageResponse();
  response.setData(storage);

  photoOwner.setPhotoURL("static/img/user/" + file.getOriginalFilename());
  personRepository.save(photoOwner);

  return response;
}





}
