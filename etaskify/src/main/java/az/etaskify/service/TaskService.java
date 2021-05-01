package az.etaskify.service;

import az.etaskify.dto.TaskDto;
import az.etaskify.model.Task;
import org.springframework.http.ResponseEntity;

public interface TaskService {
    ResponseEntity<Task> saveOrUpdateTaskByOwner(TaskDto taskDto, Long id);
}
