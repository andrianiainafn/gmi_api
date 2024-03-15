package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface NotificationRepository extends JpaRepository<Notification,String> {
}
