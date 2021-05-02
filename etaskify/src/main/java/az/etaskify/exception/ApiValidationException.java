package az.etaskify.exception;


import az.etaskify.model.InvalidParamsItem;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ApiValidationException extends ApiException {

    public ApiValidationException(List<InvalidParamsItem> invalidParamsItems) {
        super(invalidParamsItems);
    }
}
