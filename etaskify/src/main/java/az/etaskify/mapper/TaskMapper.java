package az.etaskify.mapper;

import az.etaskify.dto.TaskDto;
import az.etaskify.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task toEntity(TaskDto taskDto);

    TaskDto toDto(Task task);

    List<TaskDto> toTaskDtoList(List<Task> tasks);

    List<Task> toTaskList(List<TaskDto> taskDtos);

}
