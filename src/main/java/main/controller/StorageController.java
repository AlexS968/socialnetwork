package main.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import main.data.response.base.Response;
import main.data.response.type.Storage;
import main.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@Api
@RestController
@AllArgsConstructor
public class StorageController {

  private final StorageService storageService;

  @PostMapping("/api/v1/storage")
  public ResponseEntity<Response<Storage>> upload(
      @RequestParam(value = "file") MultipartFile file,
      @RequestParam(value = "type") String type) {

    return ResponseEntity.ok(storageService.store(file, type));
  }



}
