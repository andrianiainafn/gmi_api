package andrianianafn.gmi_api.controller;


import andrianianafn.gmi_api.entity.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin(origins = "*")
public class NotificationSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/broadcast") // /app/notifications
    @SendTo("/notification/public")
    public String receivePublicNotification(@Payload String message) {
        return "You have received a message: " + message;
    }
    @MessageMapping("/private-notification")
    public Notification receivePrivateNotification(@Payload Notification notification){
        simpMessagingTemplate.convertAndSendToUser(notification.getConcernedAccounts().get(0).getAccountId(),"/private",notification);
        return notification;
    }
}
