package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.dto.response.MaterialStatDto;
import andrianianafn.gmi_api.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin("*")
public interface MaterialRepository extends JpaRepository<Material,String> {
    Page<Material> findByActualStatusOrderByCreatedAt(String actualStatus, Pageable pageable);
    Page<Material> findAllByOwner_Department_Organization_OrganizationIdAndActualStatusOrderByCreatedAtDesc(String owner_department_organization_organizationId, String actualStatus, Pageable pageable);
    Page<Material> findAllByOwner_Department_Organization_OrganizationIdOrderByCreatedAtDesc(String owner_department_organization_organizationId, Pageable pageable);
    Page<Material> findAllByOrderByCreatedAt(Pageable pageable);

    @Query("SELECT count(*) from Material ")
    Long getMaterialNumber();

    @Query("SELECT new andrianianafn.gmi_api.dto.response.MaterialStatDto(m.actualStatus,COUNT(*)) FROM Material m GROUP BY m.actualStatus")
    List<MaterialStatDto> findStatMaterial();
}
