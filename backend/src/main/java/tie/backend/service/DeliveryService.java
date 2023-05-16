package tie.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;
import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.repository.DeliveryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository repository;

    public ArrayList<Delivery> getAllDeliveries() {
        return (ArrayList<Delivery>) repository.findAll();
    }

    public ArrayList<Delivery> getDeliveriesByPickupPointId(Long pickupPointId) {
        return repository.findByPickupPointId(pickupPointId);
    }

    public Delivery getDeliveryById(Long deliveryId) {
        Optional<Delivery> delivery = repository.findById(deliveryId);

        if (delivery.isPresent()){
            return delivery.get();
        } else {
            return null;
        }
    }


}
