package tie.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Notification;
import tie.backend.model.PickupPoint;

import java.util.List;

@Repository
public interface PickupPointRepository extends JpaRepository<PickupPoint,Long>{
    List<PickupPoint> findByName(String string);

    List<PickupPoint> findByEmail(String email);
}
