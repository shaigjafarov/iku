package az.etaskify.dto;

import az.etaskify.enums.Status;
import az.etaskify.util.LocalDateTimeSerializer;
import az.etaskify.util.LocalDatetimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDto {

    private Long id;
//    @NotBlank
    private String title;
//    @NotBlank
    private String description;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDatetimeDeserializer.class)
//    @NotNull
    private LocalDateTime deadline;
//    @NotNull
    private Status status;
//    @Valid
    private List<UserDto> userDtoList;
}
