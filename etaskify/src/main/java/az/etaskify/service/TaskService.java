package az.etaskify.service;

import az.etaskify.dto.TaskDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    ResponseEntity<String> saveOrUpdateTask(TaskDto taskDto);

    ResponseEntity<List<TaskDto>> getTasksOrganization();
}
