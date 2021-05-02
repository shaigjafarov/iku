package az.etaskify.exception;

import az.etaskify.model.AppError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorException extends RuntimeException {
    private AppError appError;
}
