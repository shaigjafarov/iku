package az.etaskify.aspect;

import az.etaskify.dto.TaskDto;
import az.etaskify.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class MailSenderExecutionAspect {

    private final EmailServiceImpl emailService;

    @AfterReturning("@annotation(az.etaskify.annotation.MailSender) && args(taskDto,id)")
    public void mailSenderExecution(TaskDto taskDto, Long id)  {
        log.info("You must write log of method");
        taskDto.getUserDtoList().forEach( userDto ->emailService.sendMail(userDto.getEmail(),taskDto.getTitle(),taskDto.getDescription()) );
    }
}