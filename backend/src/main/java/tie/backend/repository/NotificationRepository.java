package tie.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>{
    
}
