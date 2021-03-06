package az.etaskify.controller;

import az.etaskify.dto.TaskDto;
import az.etaskify.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping(value = "/save")
    public ResponseEntity<String> assignTask(@Valid @RequestBody TaskDto taskDto) {
        return taskService.saveOrUpdateTask(taskDto);
    }


    @GetMapping("/organization/tasks")
    public ResponseEntity<List<TaskDto>> getTasks() {
        return taskService.getTasksOrganization();
    }


}
