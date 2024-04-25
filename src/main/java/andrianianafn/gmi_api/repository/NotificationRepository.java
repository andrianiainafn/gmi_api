package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface NotificationRepository extends JpaRepository<Notification,String> {
    List<Notification> findAllByAccount_AccountIdOrConcernedAccountsAndNotificationTypeEquals(String account_accountId, List<Account> concernedAccounts, String notificationType);
    List<Notification> findAllByAccount_Department_Organization_OrganizationIdAndNotificationTypeEquals(String account_department_organization_organizationId, String notificationType);
}
