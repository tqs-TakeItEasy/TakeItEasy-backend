package tie.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tie.backend.model.Notification;
import tie.backend.repository.NotificationRepository;

class NotificationServiceTest {
    
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private List<Notification> dummyNotifications;
    private Notification dummyNotification1;
    private Notification dummyNotification2;

    @BeforeEach
    void setUp() {
        dummyNotifications = new ArrayList<Notification>();
        dummyNotification1 = new Notification("user1@gmail.com", "message1");
        dummyNotification2 = new Notification("user2@gmail.com", "message2");

        dummyNotifications.add(dummyNotification1);
        dummyNotifications.add(dummyNotification2);
        
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetNotifications_thenReturnAllNotifications(){
        when(notificationRepository.findAll()).thenReturn(dummyNotifications);
        
        List<Notification> returnedNotifcations = notificationService.getAllNotifications();
        
        assertEquals(dummyNotifications, returnedNotifcations);
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void whenGetNotificationById_thenReturnNotification(){
        when(notificationRepository.findById(dummyNotification1.getId())).thenReturn(Optional.ofNullable(dummyNotification1));
        
        Notification returnedNotifcation = notificationService.getNotificationById(dummyNotification1.getId());
        
        assertEquals(dummyNotifications, returnedNotifcation);
        verify(notificationRepository, times(1)).findById(dummyNotification1.getId());
    }

    @Test
    void whenGetNotificationInvalidId_thenReturnNull() {
        Long id = 200L;

        when(notificationRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        
        Notification returnedNotifcation = notificationService.getNotificationById(id);
        
        assertNull(returnedNotifcation);
        verify(notificationRepository, times(1)).findById(dummyNotification1.getId());
    }

    @Test
    void whenGetNotificationsByUserEmail_thenReturnUserNotifications(){
        List<Notification> listedDummyNotification1 = new ArrayList<Notification>(Arrays.asList(dummyNotification1));
        when(notificationRepository.findByUserEmail(dummyNotification1.getUserEmail())).thenReturn(listedDummyNotification1);
        
        List<Notification> returnedNotifcations = notificationService.getNotificationByUserEmail(dummyNotification1.getUserEmail());
        
        assertEquals(listedDummyNotification1, returnedNotifcations);
        verify(notificationRepository, times(1)).findByUserEmail(dummyNotification1.getUserEmail());
    }

    @Test
    void whenGetNotificationsByInvalidUserEmail_thenReturnEmptyList(){
        String invalidEmail = "some email";
        when(notificationRepository.findByUserEmail(invalidEmail)).thenReturn(new ArrayList<>());
        
        List<Notification> returnedNotifcations = notificationService.getNotificationByUserEmail(invalidEmail);
        
        assertThat(returnedNotifcations.isEmpty());
        verify(notificationRepository, times(1)).findByUserEmail(invalidEmail);
    }

}
