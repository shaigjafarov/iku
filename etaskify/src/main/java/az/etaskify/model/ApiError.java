package az.etaskify.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApiError {

    @JsonProperty("title")
    private String title = null;

    @JsonProperty("detail")
    private String detail = null;

    @JsonProperty("status")
    private Integer status = null;

    @JsonProperty("invalidParams")
    private List<InvalidParamsItem> invalidParams = null;

    @JsonProperty("traceId")
    private String traceId = null;

}

