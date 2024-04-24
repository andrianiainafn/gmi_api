package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DepartmentRepository extends JpaRepository<Department,String> {

    Page<Department> findAllByOrganization_OrganizationId(String organization_organizationId, Pageable pageable);
}
