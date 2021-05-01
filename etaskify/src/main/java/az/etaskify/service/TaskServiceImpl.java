package az.etaskify.service;

import az.etaskify.annotation.MailSender;
import az.etaskify.dto.TaskDto;
import az.etaskify.mapper.TaskMapper;
import az.etaskify.mapper.UserMapper;
import az.etaskify.model.Organization;
import az.etaskify.model.Task;
import az.etaskify.model.User;
import az.etaskify.repository.TaskRepository;
import az.etaskify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final OrganizationService organizationService;

    @MailSender
    @Override
    public ResponseEntity saveOrUpdateTaskByOwner( TaskDto taskDto, Long id) {
        try {
            Organization organization = organizationService.findOrganizationByOwnerId(id);
            if (organization != null) {
                List<User> organizationUsers = organization.getUsers();
                List<User> assignees= UserMapper.INSTANCE.toUserList(taskDto.getUserDtoList());
                for (User receivedUser : assignees) {
                    if (!organizationUsers.contains(receivedUser)) {
                        return new ResponseEntity<>("User doesn't exist in Organization", HttpStatus.BAD_REQUEST);
                    }
                }
                Task task= TaskMapper.INSTANCE.toEntity(taskDto);
                task.setCreatedBy(new User( id));
                task.setAssignees(assignees);
                taskRepository.save(task);
                return new ResponseEntity<>(task, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Organization doesn't exist", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
