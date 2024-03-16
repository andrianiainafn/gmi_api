package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MaterialRepository extends JpaRepository<Material,String> {
    List<Material> findByActualStatus(String actualStatus);
}
