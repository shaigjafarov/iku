package az.etaskify.exception;

public class UserNotExistException extends BaseException {

    public UserNotExistException() {
        super();
    }

    public UserNotExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserNotExistException(final String message) {
        super(message);
    }

    public UserNotExistException(final Throwable cause) {
        super(cause);
    }


}
