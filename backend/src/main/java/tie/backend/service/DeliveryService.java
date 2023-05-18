package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.repository.DeliveryRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository repository;

    public List<Delivery> getAllDeliveries() {
        return repository.findAll();
    }

    public List<Delivery> getDeliveriesByPickupPoint(PickupPoint pickupPoint) {
        return repository.findByPickupPoint(pickupPoint);
    }

    public Delivery getDeliveryById(Long deliveryId) {
        Optional<Delivery> delivery = repository.findById(deliveryId);

        if (delivery.isPresent()){
            return delivery.get();
        } else {
            return null;
        }
    }

    public List<Delivery> getDeliveryByPackageId(Long id) {
        return repository.findByPackageId(id);
    }


}
