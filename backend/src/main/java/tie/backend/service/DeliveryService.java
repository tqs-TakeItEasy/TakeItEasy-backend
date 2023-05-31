package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import tie.backend.exceptions.ResourceNotFoundException;
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

    public Delivery addDelivery(Delivery delivery) {
        Optional<Delivery> pickupPointByName = repository.findByPackageId(delivery.getPackageId());

        if (pickupPointByName.isPresent()){
            throw new ResponseStatusException(HttpStatus.OK, "This Delivery's Package ID already exists");
        } else {
            repository.save(delivery);
            return delivery;
        }
    }

    public Delivery updateDeliveryStatus(Delivery delivery) throws ResourceNotFoundException {
        Optional<Delivery> existingDelivery = repository.findById(delivery.getId());

        if (existingDelivery.isPresent()){
            Delivery updatDelivery = existingDelivery.get();
            updatDelivery.setStatus(delivery.getStatus());
            repository.save(updatDelivery);
            return updatDelivery;
        } else {
            throw new ResourceNotFoundException("This Delivery does not exist!");
        }
    }
}
