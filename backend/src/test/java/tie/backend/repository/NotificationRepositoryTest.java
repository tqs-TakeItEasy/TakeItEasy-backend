package tie.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tie.backend.model.Notification;

@DataJpaTest
class NotificationRepositoryTest {
    
    @Autowired 
    private TestEntityManager testEntityManager;

    @Autowired
    private NotificationRepository notificationRepository;

    private ArrayList<Notification> dummyNotifications;
    private Notification dummyNotification1;
    private Notification dummyNotification2;

    @BeforeEach
    void setUp(){
        dummyNotifications = new ArrayList<>();
        dummyNotification1 = new Notification("userEmail1", "message1");
        dummyNotification2 = new Notification("userEmail2", "message2");

        dummyNotifications.add(dummyNotification1);
        dummyNotifications.add(dummyNotification2);
    }

    @Test
    void whenGetNotifications_thenReturnAllNotifications(){
        testEntityManager.persistAndFlush(dummyNotification1);
        testEntityManager.persistAndFlush(dummyNotification2);

        List<Notification> returnedNotifications = notificationRepository.findAll();

        assertEquals(dummyNotifications, returnedNotifications);
    }

    @Test
    void whenGetNotificationById_thenReturnNotification(){
        testEntityManager.persistAndFlush(dummyNotification1);

        Notification returnedNotification = notificationRepository.findById(dummyNotification1.getId()).orElse(null);
        
        assertEquals(dummyNotification1, returnedNotification);
    }

    @Test
    void whenGetNotificationByInvalidId_thenReturnNull(){
        Long invalidId = 200L;
        Notification returnedNotification = notificationRepository.findById(invalidId).orElse(null);

        assertNull(returnedNotification);
    }

    @Test
    void whenGetNotificationByUserEmail_thenReturnNotification(){
        testEntityManager.persistAndFlush(dummyNotification1);
        List<Notification> dummyNotification1List = new ArrayList<>(Arrays.asList(dummyNotification1));
        List<Notification> returnedNotifications = notificationRepository.findByUserEmail(dummyNotification1.getUserEmail());
        
        assertEquals(dummyNotification1List, returnedNotifications);
    }

    @Test
    void whenGetNotificationByInvalidUserEmail_thenReturnEmptyList(){
        String invalidEmail = "some email";
        List<Notification> returnedNotifications = notificationRepository.findByUserEmail(invalidEmail);

        assertThat(returnedNotifications).isEmpty();
    }
}
