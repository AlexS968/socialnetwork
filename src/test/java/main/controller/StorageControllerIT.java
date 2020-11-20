package main.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Files;
import main.AbstractIntegrationIT;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;

public class StorageControllerIT extends AbstractIntegrationIT {

  @WithUserDetails("user@user.ru")
  @Test
  public void shouldStore() throws Exception {

    File initialFile = new File("C:\\Users\\vitta\\Desktop\\Storage\\1.jpg");


    byte [] bytes =  Files.readAllBytes(initialFile.toPath());
    long bytesLength = bytes.length;

    MockMultipartFile file
        = new MockMultipartFile(
        "file",
        "1.jpg",
        MediaType.TEXT_PLAIN_VALUE,
        bytes
    );


    mockMvc.perform(multipart("/api/v1/storage").file(file).param("type", "IMAGE"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.bytes").value(bytesLength));


  }

}
