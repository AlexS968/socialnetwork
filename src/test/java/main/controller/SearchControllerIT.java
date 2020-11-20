package main.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import main.AbstractIntegrationIT;
import org.junit.Test;
import org.springframework.security.test.context.support.WithUserDetails;

@WithUserDetails("user@user.ru")
public class SearchControllerIT extends AbstractIntegrationIT {


  @Test
  public void shouldFindPerson() throws Exception {

    mockMvc.perform(get("/api/v1/users/search")
        .param("age_from", "10")
        .param("age_to", "13")
        .param("first_name", "Иван")
        .param("country", "Россия"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.total").value("2"))
        .andExpect(jsonPath("$.data[0].email").value("user02@user.ru"));

  }

//  @Test
//  public void shouldFindPost() throws Exception {
//
//    mockMvc.perform(get("/api/v1/post")
//        .param("text", "Post")
//        .param("date_from", "1605796920354") //
//        .param("date_to", "1574175437043") //
//        .param("author", "Иван")
//        .param("tags", "")
//       // .param("offset", "0")
//      //  .param("itemPerPage", "20")
//        .accept("application/json"))
//        .andDo(print())
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.total").value("2"))
//       // .andExpect(jsonPath("$.data[0].author.email").value("user03@user.ru"))
//    ;
//
//  }

}
