package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.model.Store;
import tie.backend.repository.DeliveryRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository repository;

    public List<Delivery> getAllDeliveries() {
        return repository.findAll();
    }

    public Optional<Delivery> getDeliveryById(Long deliveryId) {
        return repository.findById(deliveryId);
    }

    public Optional<Delivery> getDeliveryByPackageId(Long id) {
        return repository.findByPackageId(id);
    }
    
    public List<Delivery> getDeliveriesByPickupPoint(PickupPoint pickupPoint) {
        return repository.findByPickupPoint(pickupPoint);
    }

    public List<Delivery> getDeliveriesByStore(Store store){
        return repository.findByStore(store);
    }

}
