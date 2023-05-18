package tie.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.model.Store;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long>{
    List<Delivery> findByPackageId(Long id);
    List<Delivery> findByPickupPoint(PickupPoint pickupPoint);
    List<Delivery> findByStore(Store store);
}
