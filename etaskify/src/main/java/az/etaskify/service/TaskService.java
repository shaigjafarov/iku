package az.etaskify.service;

import az.etaskify.dto.TaskDto;
import az.etaskify.model.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    ResponseEntity<Task> saveOrUpdateTask(TaskDto taskDto);

    ResponseEntity<List<TaskDto>> getTaskById(Long id);
}
