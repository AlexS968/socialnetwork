package main.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import main.data.response.base.ListResponse;
import main.data.response.type.PersonInPersonList;
import main.model.Person;
import org.springframework.data.domain.Page;

@Data
public class ListPersonResponse extends ListResponse {
  @JsonProperty(value = "data")
  private List<PersonInPersonList> data = new ArrayList<>();

  public ListPersonResponse(Page<Person> people) {
    this.setOffset((int) people.getPageable().getOffset());
    this.setPerPage(people.getNumberOfElements());
    this.setError("string");
    this.setTimestamp(Instant.now().toEpochMilli());
    this.setTotal(people.getTotalElements());

    people.forEach(p ->
      {{
        data.add(new PersonInPersonList(p));
      }});


  }
}
