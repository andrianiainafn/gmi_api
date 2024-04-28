package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.Notification;
import andrianianafn.gmi_api.entity.Organization;
import andrianianafn.gmi_api.repository.AccountRepository;
import andrianianafn.gmi_api.repository.NotificationRepository;
import andrianianafn.gmi_api.service.AuthService;
import andrianianafn.gmi_api.service.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final AuthService authService;
    private final AccountRepository accountRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, AuthService authService, AccountRepository accountRepository) {
        this.notificationRepository = notificationRepository;
        this.authService = authService;
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification createNewNotification(String token, List<String> accountConcerned, String notificationType) {
        List<Account> accounts =  accountRepository.findAllById(accountConcerned);
        Account account = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        Notification notification = Notification.builder()
                .createdAt(new Date())
                .updatedAt(new Date())
                .concernedAccounts(accounts)
                .account(account)
                .notificationType(notificationType)
                .isRead(false)
                .build();
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getMovementNewNotification(String token) {
        String accountId = authService.decodeToken(token);
        Account account = accountRepository.findById(accountId).orElse(null);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        return notificationRepository.findAllByAccount_AccountIdOrConcernedAccountsAndNotificationTypeEquals(accountId,accounts,"request");
    }

    @Override
    public List<Notification> getRequestNotification(String token) {
        Account account = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        if(account != null){
            Organization organization = account.getDepartment().getOrganization();
            return notificationRepository.findAllByAccount_Department_Organization_OrganizationIdAndNotificationTypeEquals(organization.getOrganizationId(), "request");
        }
        return new ArrayList<>();
    }

    @Override
    public List<Notification> getReportNotification(String token) {
        return null;
    }

    @Override
    public Notification markNotificationAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
        return notification;
    }
}