package az.etaskify.exception;

import az.etaskify.model.InvalidParamsItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiException extends RuntimeException {

    private String title;
    private String detail;
    private HttpStatus status;
    private List<InvalidParamsItem> invalidParams;

    ApiException(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    ApiException(List<InvalidParamsItem> invalidParamsItem) {
        this.invalidParams = invalidParamsItem;
    }

}
