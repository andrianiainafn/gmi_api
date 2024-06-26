package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.entity.RequestPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PriorityRepository extends JpaRepository<RequestPriority,String> {

}
