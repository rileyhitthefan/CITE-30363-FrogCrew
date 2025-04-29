package edu.tcu.cs.frogcrewbackend.notification.dto;

public record NotificationDTO(
        Integer notificationId,
        String message,
        Boolean read,
        String date
) {
}
