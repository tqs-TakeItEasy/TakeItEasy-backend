package tie.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>{

    List<Notification> findByUserEmail(String string);
    
}
