package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tie.backend.model.Notification;
import tie.backend.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
    
        if (notification.isPresent()){
            return notification.get();
        } else {
            return null;
        }
    }

    public List<Notification> getNotificationByUserEmail(String invalidEmail) {
        return notificationRepository.findByUserEmail(invalidEmail);
    }
    
}
