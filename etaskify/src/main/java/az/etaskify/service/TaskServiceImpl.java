package az.etaskify.service;

import az.etaskify.annotation.MailSender;
import az.etaskify.dto.TaskDto;
import az.etaskify.exception.ApiException;
import az.etaskify.mapper.TaskMapper;
import az.etaskify.mapper.UserMapper;
import az.etaskify.model.Organization;
import az.etaskify.model.Task;
import az.etaskify.model.User;
import az.etaskify.repository.TaskRepository;
import az.etaskify.util.ValidationObjects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final OrganizationService organizationService;

    @MailSender
    @Override
    public ResponseEntity saveOrUpdateTaskByOwner(TaskDto taskDto, Long id) {
        Organization organization = organizationService.findOrganizationByOwnerId(id);
        ValidationObjects.controlObjectNotNull(organization);
        List<User> organizationUsers = organization.getUsers();
        List<User> assignees = UserMapper.INSTANCE.toUserList(taskDto.getUserDtoList());
        checkUsersExist(organizationUsers, assignees);

        Task task = TaskMapper.INSTANCE.toEntity(taskDto);
        task.setCreatedBy(new User(id));
        task.setAssignees(assignees);
        task.setOrganization(organization);
        taskRepository.save(task);
        return new ResponseEntity<>(task, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<TaskDto>> getTaskById(Long id) {
        Organization organization = organizationService.findOrganizationByOwnerId(id);
        List<Task> taskList= taskRepository.findAllByOrganization(organization);
        return new ResponseEntity<>(TaskMapper.INSTANCE.toTaskDtoList(taskList), HttpStatus.OK);
    }

    private void checkUsersExist(List<User> existUsers, List<User> receivedUsers) {
        List<User> userList = receivedUsers.stream().filter(user -> !existUsers.contains(user)).collect(Collectors.toList());
        if (!userList.isEmpty()) {
            throw new ApiException("User not exist",userList.toString(), HttpStatus.NOT_FOUND, null);
        }


    }
}
