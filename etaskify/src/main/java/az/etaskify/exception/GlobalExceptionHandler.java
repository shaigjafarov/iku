package az.etaskify.exception;

import az.etaskify.model.ApiError;
import az.etaskify.model.InvalidParamsItem;
import az.etaskify.util.AppMDC;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorAttributes errorAttributes;

    public GlobalExceptionHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @ExceptionHandler({ApiErrorException.class})
    public final ResponseEntity<Object> handleApiException(ApiErrorException ex) {
        return new ResponseEntity<>(ex.getAppError(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ApiException.class})
    public final ResponseEntity<Object> handleApiException(ApiException ex) {

        ApiError apiError = ApiError.builder()
                .title(ex.getTitle())
                .detail(ex.getDetail())
                .status(ex.getStatus().value())
                .traceId(AppMDC.getTraceID())
                .invalidParams(ex.getInvalidParams())
                .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), ex.getStatus());
    }

    @ExceptionHandler({ApiValidationException.class})
    private ResponseEntity<Object> handleApiValidationException(ApiValidationException ex) {
        List<InvalidParamsItem> invalidParams = ex.getInvalidParams();

        ApiError apiError = ApiError.builder()
                .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .detail(getDetailFromInvalidParams(invalidParams))
                .status(HttpStatus.BAD_REQUEST.value())
                .traceId(AppMDC.getTraceID())
                .invalidParams(invalidParams)
                .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ApiInternalServerErrorException.class})
    private ResponseEntity<Object> handleApiValidationException(ApiInternalServerErrorException ex) {
        String detail = ex.getDetail();

        ApiError apiError = ApiError.builder()
                .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .detail(detail)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .traceId(AppMDC.getTraceID())
                .invalidParams(new ArrayList<InvalidParamsItem>() {
                })
                .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
        if (ex instanceof AccessDeniedException) {
            return this.handleHttpAccessDenied((AccessDeniedException) ex, request);
        } else if (ex instanceof AuthenticationServiceException) {
            return this.handleHttpAccessDenied((AuthenticationServiceException) ex, request);
        }
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(request, false);
        String message = (String) errorAttributes.get("message");
        ApiError apiError = ApiError.builder()
                .title(status.getReasonPhrase())
                .detail(message)
                .status(status.value())
                .traceId(AppMDC.getTraceID())
                .invalidParams(new ArrayList<>())
                .build();

        return new ResponseEntity<>(apiError, headers, status);
    }

    protected ResponseEntity<Object> handleHttpAccessDenied(AccessDeniedException ex, WebRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;
        HttpHeaders headers = new HttpHeaders();

        ApiError apiError = ApiError.builder()
                .title(status.getReasonPhrase())
                .detail("Access Denied")
                .status(status.value())
                .traceId(AppMDC.getTraceID())
                .invalidParams(new ArrayList<InvalidParamsItem>() {
                })
                .build();

        return new ResponseEntity<>(apiError, headers, status);
    }

    protected ResponseEntity<Object> handleHttpAccessDenied(AuthenticationServiceException ex, WebRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        HttpHeaders headers = new HttpHeaders();

        ApiError apiError = ApiError.builder()
                .title(status.getReasonPhrase())
                .detail("Authentication failed")
                .status(status.value())
                .traceId(AppMDC.getTraceID())
                .invalidParams(new ArrayList<InvalidParamsItem>() {
                })
                .build();

        return new ResponseEntity<>(apiError, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorTitle = status.getReasonPhrase();
        String message = ex.getMessage();

        ApiError apiError = ApiError.builder()
                .title(errorTitle)
                .detail(message)
                .status(status.value())
                .traceId(AppMDC.getTraceID())
                .build();

        return new ResponseEntity<>(apiError, headers, status);
    }

    private String getDetailFromInvalidParams(List<InvalidParamsItem> invalidParams) {
        String detail = invalidParams.stream()
                .map(invalidParam -> invalidParam.getName() + " " + invalidParam.getReason())
                .reduce("", (next, current) -> current + "," + next);
        return detail.substring(0, detail.length() - 1);
    }
//
//    @ExceptionHandler({UserNotExistException.class})
//    public ResponseEntity<Object> userNotExistException(
//            final UserNotExistException ex) {
//        //   logger.error("User already exists", ex);
//        return new ResponseEntity<>("User not exist "+ex.getMessage(),  HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler({ValidationException.class})
//    public ResponseEntity<Object> validationException(final ValidationException ex) {
//        //   logger.error("User already exists", ex);
//        return new ResponseEntity<>(ex.getMessage(),  HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler({UsernameNotFoundException.class})
//    public ResponseEntity<Object> validationException(final UsernameNotFoundException ex) {
//        //   logger.error("User already exists", ex);
//        return new ResponseEntity<>(ex.getMessage(),  HttpStatus.BAD_REQUEST);
//    }
//
//
//    @ExceptionHandler({AlreadyExistsException.class})
//    public ResponseEntity<Object> validationException(final AlreadyExistsException ex) {
//        //   logger.error("User already exists", ex);
//        return new ResponseEntity<>(ex.getMessage(),  HttpStatus.BAD_REQUEST);
//    }
}
