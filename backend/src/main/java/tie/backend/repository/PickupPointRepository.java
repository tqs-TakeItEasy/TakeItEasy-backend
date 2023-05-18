package tie.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Notification;
import tie.backend.model.PickupPoint;

import java.util.List;
import java.util.Optional;

@Repository
public interface PickupPointRepository extends JpaRepository<PickupPoint,Long>{
    Optional<PickupPoint> findByName(String string);

    Optional<PickupPoint> findByEmail(String email);
}
