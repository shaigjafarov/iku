package az.etaskify.util;

import az.etaskify.exception.ValidationException;

import java.util.Objects;


public class ValidationObjects {

    public static void controlObjectNotNull(Object object, String exceptionMessage){
        if (!Objects.nonNull(object)){
            throw new ValidationException(exceptionMessage);
        }

    }

}
