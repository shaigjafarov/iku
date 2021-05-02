package az.etaskify.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        List<String>  fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());
        List<String>  globalErrors = ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " +error.getDefaultMessage() )
                .collect(Collectors.toList());
        errors.addAll(fieldErrors);
        errors.addAll(globalErrors);
        return new ResponseEntity<>(errors, headers, status);
    }

    @ExceptionHandler({UserNotExistException.class})
    public ResponseEntity<Object> userNotExistException(
            final UserNotExistException ex) {
     //   logger.error("User already exists", ex);
        return new ResponseEntity<>("User not exist "+ex.getMessage(),  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> validationException(final ValidationException ex) {
     //   logger.error("User already exists", ex);
        return new ResponseEntity<>(ex.getMessage(),  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Object> validationException(final UsernameNotFoundException ex) {
     //   logger.error("User already exists", ex);
        return new ResponseEntity<>(ex.getMessage(),  HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<Object> validationException(final AlreadyExistsException ex) {
     //   logger.error("User already exists", ex);
        return new ResponseEntity<>(ex.getMessage(),  HttpStatus.BAD_REQUEST);
    }


}