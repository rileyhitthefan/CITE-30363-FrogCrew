package edu.tcu.cs.frogcrewbackend.notification;

import edu.tcu.cs.frogcrewbackend.game.Game;
import edu.tcu.cs.frogcrewbackend.notification.converter.NotificationDTOToNotificationConverter;
import edu.tcu.cs.frogcrewbackend.notification.converter.NotificationToNotificationDTOConverter;
import edu.tcu.cs.frogcrewbackend.notification.dto.NotificationDTO;
import edu.tcu.cs.frogcrewbackend.system.Result;
import edu.tcu.cs.frogcrewbackend.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationToNotificationDTOConverter notificationToNotificationDTOConverter;
    private final NotificationDTOToNotificationConverter notificationDTOToNotificationConverter;

    public NotificationController(NotificationService notificationService, NotificationToNotificationDTOConverter notificationToNotificationDTOConverter, NotificationDTOToNotificationConverter notificationDTOToNotificationConverter) {
        this.notificationService = notificationService;
        this.notificationToNotificationDTOConverter = notificationToNotificationDTOConverter;
        this.notificationDTOToNotificationConverter = notificationDTOToNotificationConverter;
    }

    @PostMapping("/{userId}")
    public Result sendNotificationToUser(@PathVariable Integer userId, @RequestBody @Valid String message) {
        Notification notification = this.notificationService.sendNotificationToUser(userId, message);
        NotificationDTO notificationDTO = this.notificationToNotificationDTOConverter.convert(notification);
        return new Result(true, StatusCode.SUCCESS,  "Notification sent to user " + userId, notificationDTO);
    }

    @GetMapping("/{userId}")
    public Result findNotificationsByUser(@PathVariable Integer userId) {
        List<Notification> notifications = this.notificationService.findNotificationsByUser(userId);
        List<NotificationDTO> notificationDTOS = notifications.stream()
                .map(this.notificationToNotificationDTOConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS,  "Notifications found for user " + userId, notificationDTOS);
    }
}
