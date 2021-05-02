package az.etaskify.util;

import az.etaskify.exception.ApiValidationException;
import az.etaskify.model.InvalidParamsItem;

import java.util.Collections;
import java.util.Objects;


public class ValidationObjects {

    public static void controlObjectNotNull(Object object){
        if (object!=null){
            throw new ApiValidationException(
                    Collections.singletonList(new InvalidParamsItem(object.getClass().getName(), "does not exist"))
            );
        }

    }

}
