package az.etaskify.mapper;

import az.etaskify.dto.TaskDto;
import az.etaskify.dto.UserDto;
import az.etaskify.model.Task;
import az.etaskify.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.persistence.ManyToOne;
import java.util.List;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task toEntity(TaskDto taskDto);

    TaskDto toDto(Task task);

    List<TaskDto> toTaskDtoList(List<Task> tasks);

    List<Task> toTaskList(List<TaskDto> taskDtos);

}
