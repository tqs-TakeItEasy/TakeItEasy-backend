package tie.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Delivery;

import java.util.ArrayList;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long>{

    ArrayList<Delivery> findByPickupPointId(Long pickupPointId);
}
