package edu.tcu.cs.frogcrewbackend.notification;

import edu.tcu.cs.frogcrewbackend.member.Member;
import edu.tcu.cs.frogcrewbackend.member.UserRepository;
import edu.tcu.cs.frogcrewbackend.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public Notification sendNotificationToUser(Integer userId, String message) {
        Member member = this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("member", userId));

        Notification notification = new Notification();
        notification.setMessage(message.strip());
        LocalDate localDate = LocalDate.now();
        String date = localDate.toString();
        notification.setDate(date);
        notification.setUser(member);
        notification.setRead(false);

        member.addNotification(notification);
        return this.notificationRepository.save(notification);
    }

    public List<Notification> findNotificationsByUser(Integer userId) {
        Member member = this.userRepository.findById(userId)
            .orElseThrow(() -> new ObjectNotFoundException("member", userId));
        List<Notification> notifications = member.getNotifications();
        return notifications;
    }
}
