package edu.tcu.cs.frogcrewbackend.notification.converter;

import edu.tcu.cs.frogcrewbackend.notification.Notification;
import edu.tcu.cs.frogcrewbackend.notification.dto.NotificationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationToNotificationDTOConverter implements Converter<Notification, NotificationDTO> {

    @Override
    public NotificationDTO convert(Notification source) {
        NotificationDTO notificationDTO = new NotificationDTO(
                source.getNotificationId(),
                source.getMessage(),
                source.getRead(),
                source.getDate()
        );
        return notificationDTO;
    }
}
