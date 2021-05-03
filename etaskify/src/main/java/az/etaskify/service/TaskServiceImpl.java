package az.etaskify.service;

import az.etaskify.dto.TaskDto;
import az.etaskify.dto.UserDto;
import az.etaskify.exception.UserNotExistException;
import az.etaskify.mapper.TaskMapper;
import az.etaskify.mapper.UserMapper;
import az.etaskify.model.Organization;
import az.etaskify.model.Task;
import az.etaskify.model.User;
import az.etaskify.repository.TaskRepository;
import az.etaskify.repository.UserRepository;
import az.etaskify.util.SecurityContextUtility;
import az.etaskify.util.ValidationObjects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final OrganizationService organizationService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Transactional
    @Override
    public ResponseEntity<String> saveOrUpdateTask(TaskDto taskDto) {
        Organization organization = organizationService.findOrganizationByEmail(SecurityContextUtility.getLoggedUsername());
        ValidationObjects.controlObjectNotNull(organization, "organization not exist");
        List<User> organizationUsers = organization.getUsers();
        List<User> assignees = UserMapper.INSTANCE.toUserList(taskDto.getUserDtoList());
        checkUsersExist(organizationUsers, assignees);

        Task task = TaskMapper.INSTANCE.toEntity(taskDto);
        userRepository.findUserEntityByEmail(SecurityContextUtility.getLoggedUsername());
        Optional<User> optionalUser = userRepository.findUserEntityByEmail(SecurityContextUtility.getLoggedUsername());
        task.setCreatedBy(optionalUser.get());
        task.setAssignees(assignees);
        task.setOrganization(organization);
        taskRepository.save(task);
        sendMailTaskAssigned(taskDto);
        return new ResponseEntity<>("Task is created successful", HttpStatus.CREATED);


    }


    private void sendMailTaskAssigned(TaskDto taskDto) {
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        Consumer<UserDto> userDtoConsumer = userDto -> emailService.sendMail(userDto.getEmail(), taskDto.getTitle(), taskDto.getDescription());
        emailExecutor.execute(() -> taskDto.getUserDtoList().forEach(userDtoConsumer));
        emailExecutor.shutdown();
    }

    @Override
    public ResponseEntity<List<TaskDto>> getTasksOrganization() {
        Organization organization = organizationService.findOrganizationByEmail(SecurityContextUtility.getLoggedUsername());
        List<Task> taskList = taskRepository.findAllByOrganization(organization);
        return new ResponseEntity<>(TaskMapper.INSTANCE.toTaskDtoList(taskList), HttpStatus.OK);
    }

    private void checkUsersExist(List<User> existUsers, List<User> receivedUsers) {
        List<User> userList = receivedUsers.stream().filter(existUsers::contains).collect(Collectors.toList());
        if (userList.isEmpty()) {
            throw new UserNotExistException(userList.toString());
        }


    }
}
