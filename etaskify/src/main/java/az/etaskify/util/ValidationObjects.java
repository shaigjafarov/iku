package az.etaskify.util;

import az.etaskify.exception.ValidationException;


public class ValidationObjects {

    public static void controlObjectNotNull(Object object, String exceptionMessage) {
        if (object == null) {
            throw new ValidationException(exceptionMessage);
        }

    }

}
