package andrianianafn.gmi_api.controller;


import andrianianafn.gmi_api.entity.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/notification") // /app/notifications
    @SendTo("/notification/public")
    public Notification receivePublicNotification(@Payload Notification notification){
        return notification;
    }
    @MessageMapping("/private-notification")
    public Notification receivePrivateNotification(@Payload Notification notification){
        simpMessagingTemplate.convertAndSendToUser(notification.getConcernedAccounts().get(0).getAccountId(),"/private",notification);
        return notification;
    }
}
