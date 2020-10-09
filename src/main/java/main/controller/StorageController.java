package main.controller;

import main.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StorageController {

  @Autowired
  StorageService storageService;


  @PostMapping("/api/v1/storage")
  public ResponseEntity upload(
      @RequestParam(value = "file") MultipartFile file,
      @RequestParam(value = "type") String type) {

    return ResponseEntity.ok(storageService.store(file, type));
  }

//  @GetMapping("/api/v1/static/img/user/{fileName}")
//  public ResponseEntity serve(@PathVariable String pathToFile){
//    System.out.println("---------------" + pathToFile);
//    return ResponseEntity.ok("pathToFile");
//  }

}
