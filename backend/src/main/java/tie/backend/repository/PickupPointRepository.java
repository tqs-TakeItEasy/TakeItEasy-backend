package tie.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.PickupPoint;
import tie.backend.model.PickupPointStatus;

@Repository
public interface PickupPointRepository extends JpaRepository<PickupPoint,Long>{
    Optional<PickupPoint> findByName(String string);
    Optional<PickupPoint> findByEmail(String email);
    List<PickupPoint> findByStatus(PickupPointStatus pickupPointStatus);
}
