package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RequestRepository extends JpaRepository<Request,String> {
}
