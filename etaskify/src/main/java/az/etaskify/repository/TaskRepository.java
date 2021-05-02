package az.etaskify.repository;

import az.etaskify.model.Organization;
import az.etaskify.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByOrganization(Organization organization);
}
