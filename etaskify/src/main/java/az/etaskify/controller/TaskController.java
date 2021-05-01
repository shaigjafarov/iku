package az.etaskify.controller;

import az.etaskify.dto.TaskDto;
import az.etaskify.model.Task;
import az.etaskify.model.User;
import az.etaskify.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping(value = "/save")
    public ResponseEntity<Task> signUpOrganization(@Valid @RequestBody TaskDto taskDto, @RequestParam Long id ) {
        return taskService.saveOrUpdateTaskByOwner(taskDto, id);
    }





}
