package main.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import main.data.response.base.ListResponse;
import main.data.response.type.SingleTag;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListTagResponse extends ListResponse<SingleTag> {
    private List<SingleTag> data;

    public void addTag(SingleTag tag) {
        data.add(tag);
    }
}
