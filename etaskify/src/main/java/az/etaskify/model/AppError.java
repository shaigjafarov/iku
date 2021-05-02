package az.etaskify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
public class AppError {

  @JsonProperty("errorCode")
  @NotNull
  private ErrorCodeEnum errorCode;

  @JsonProperty("message")
  private String message;

  @JsonProperty("target")
  private String target;

  @JsonProperty("debug")
  private String debug;

    /**
     * Application error code
     */
    public enum ErrorCodeEnum {
        GENERICERROR("genericError"),
        INVALIDARGUMENT("invalidArgument");

        private String value;

        ErrorCodeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static ErrorCodeEnum fromValue(String text) {
            for (ErrorCodeEnum b : ErrorCodeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }
}

