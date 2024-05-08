package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role,String> {
    List<Role> findAllByRoleIdIsIn(List<String> roles);
    List<Role> findAllByOrganization_OrganizationId(String organizationId);
}
