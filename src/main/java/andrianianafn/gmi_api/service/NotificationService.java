package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNewNotification(String token, List<String> accountConcerned,String notificationType);

    List<Notification> getMovementNewNotification(String token);

    List<Notification> getRequestNotification(String token);

    List<Notification> getReportNotification(String token);
}
