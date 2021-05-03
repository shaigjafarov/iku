package az.etaskify.service;

import az.etaskify.dto.TaskDto;
import az.etaskify.dto.UserDto;
import az.etaskify.exception.UserNotExistException;
import az.etaskify.model.Organization;
import az.etaskify.model.Task;
import az.etaskify.model.User;
import az.etaskify.repository.TaskRepository;
import az.etaskify.repository.UserRepository;
import az.etaskify.service.impl.TaskServiceImpl;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @InjectMocks
    private TaskServiceImpl taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private OrganizationService organizationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;

    @Test
    void givenOrgWithNoUserWhenSaveOrUpdateThenThrowUserNotExistException() {
        TaskDto taskDto = new TaskDto();
        taskDto.setUserDtoList(Collections.emptyList());
        Organization organization = new Organization();
        organization.setUsers(Collections.emptyList());
        when(organizationService.findOrganizationByEmail(any())).thenReturn(organization);
        Assertions.assertThrows(UserNotExistException.class, () -> taskService.saveOrUpdateTask(taskDto));
    }

    @Test
    void givenTaskWithAnAssigneeWhenSaveOrUpdateThenSendEmail() {
        User user = new User();
        user.setId(1L);
        Organization organization = new Organization();
        organization.setUsers(Collections.singletonList(user));
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        TaskDto taskDto = new TaskDto();
        taskDto.setUserDtoList(Collections.singletonList(userDto));
        when(organizationService.findOrganizationByEmail(any())).thenReturn(organization);
        when(userRepository.findUserEntityByEmail(any())).thenReturn(Optional.of(user));
        when(taskRepository.save(any())).thenReturn(null);
        Assertions.assertEquals(HttpStatus.CREATED, taskService.saveOrUpdateTask(taskDto).getStatusCode());
        verify(emailService, times(1)).sendMail(any(), any(), any());
    }

    @Test
    void givenTaskWithoutAssigneesWhenSaveOrUpdateThenDoNotSendEmail() {
        User user = new User();
        user.setId(1L);
        Organization organization = new Organization();
        organization.setUsers(Collections.singletonList(user));
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        TaskDto taskDto = new TaskDto();
        taskDto.setUserDtoList(Collections.singletonList(userDto));
        when(organizationService.findOrganizationByEmail(any())).thenReturn(organization);
        when(userRepository.findUserEntityByEmail(any())).thenReturn(Optional.of(user));
        when(taskRepository.save(any())).thenAnswer(i -> {
            Task task = i.getArgument(0);
            task.setAssignees(Collections.emptyList());
            return task;
        });
        verify(emailService, times(0)).sendMail(any(), any(), any());
        Assertions.assertEquals(HttpStatus.CREATED, taskService.saveOrUpdateTask(taskDto).getStatusCode());
    }

    @Test
    void givenOrgEmailWhenGetTasksOfOrgThenReturnTasks() {
        when(organizationService.findOrganizationByEmail(any())).thenReturn(new Organization());
        when(taskRepository.findAllByOrganization(any())).thenReturn(Collections.emptyList());
        Assertions.assertEquals(HttpStatus.OK, taskService.getTasksOrganization().getStatusCode());
    }
}