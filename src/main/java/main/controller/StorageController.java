package main.controller;

import lombok.AllArgsConstructor;
import main.data.response.StorageResponse;
import main.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class StorageController {

  private final StorageService storageService;

  @PostMapping("/api/v1/storage")
  public ResponseEntity<StorageResponse> upload(
      @RequestParam(value = "file") MultipartFile file,
      @RequestParam(value = "type") String type) {

    return ResponseEntity.ok(storageService.store(file, type));
  }



}
