package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.entity.MaterialStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MaterialStatusRepository extends JpaRepository<MaterialStatus,String> {
}
