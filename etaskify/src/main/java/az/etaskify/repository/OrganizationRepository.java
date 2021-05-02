package az.etaskify.repository;

import az.etaskify.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {


@Query(value = "select o from Organization  o join  o.users u where u.id=:userId")
    Organization findOrganizationByOwnerId(Long userId);


@Query(value = "select o from Organization  o join  o.users u where u.email=:email")
    Organization findOrganizationByEmail(String email);
}
