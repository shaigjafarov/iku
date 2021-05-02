package az.etaskify.service;

import az.etaskify.dto.TaskDto;
import az.etaskify.model.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    ResponseEntity<Task> saveOrUpdateTaskByOwner(TaskDto taskDto, Long id);

    ResponseEntity<List<TaskDto>> getTaskById(Long id);
}
