package edu.tcu.cs.frogcrewbackend.notification.converter;

import edu.tcu.cs.frogcrewbackend.notification.Notification;
import edu.tcu.cs.frogcrewbackend.notification.dto.NotificationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationDTOToNotificationConverter implements Converter<NotificationDTO, Notification>{
    @Override
    public Notification convert(NotificationDTO source) {
        Notification noti = new Notification();
        noti.setNotificationId(source.notificationId());
        noti.setMessage(source.message());
        noti.setRead(source.read());
        noti.setDate(source.date());
        return noti;
    }
}
