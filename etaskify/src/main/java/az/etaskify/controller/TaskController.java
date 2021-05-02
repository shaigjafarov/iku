package az.etaskify.controller;

import az.etaskify.dto.TaskDto;
import az.etaskify.model.Task;
import az.etaskify.model.User;
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
    public ResponseEntity<Task> assignTask(@Valid @RequestBody TaskDto taskDto, @RequestParam Long id ) {
        return taskService.saveOrUpdateTaskByOwner(taskDto, id);
    }


    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasks( @RequestParam Long id ) {
        return taskService.getTaskById(id);
    }






}