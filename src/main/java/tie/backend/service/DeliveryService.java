package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import tie.backend.exception.ResourceNotFoundException;
import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.model.Store;
import tie.backend.repository.DeliveryRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Optional<Delivery> getDeliveryById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId);
    }

    public Optional<Delivery> getDeliveryByPackageId(Long id) {
        return deliveryRepository.findByPackageId(id);
    }
    
    public List<Delivery> getDeliveriesByPickupPoint(PickupPoint pickupPoint) {
        return deliveryRepository.findByPickupPoint(pickupPoint);
    }

    public List<Delivery> getDeliveriesByStore(Store store){
        return deliveryRepository.findByStore(store);
    }

    public Delivery addDelivery(Delivery delivery) {
        Optional<Delivery> deliveryPackageId = deliveryRepository.findByPackageId(delivery.getPackageId());

        if (deliveryPackageId.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Delivery's Package ID already exists");
        } else {
            deliveryRepository.save(delivery);
            return delivery;
        }
    }

    public Delivery updateDelivery(Delivery delivery) throws ResourceNotFoundException {
        Optional<Delivery> existingDelivery = deliveryRepository.findById(delivery.getId());

        if (existingDelivery.isPresent()){
            Delivery deliveryToUpdate = existingDelivery.get();
            deliveryToUpdate.update(delivery);
            Delivery updatedDelivery = deliveryRepository.save(deliveryToUpdate);
            return updatedDelivery;
        } else {
            throw new ResourceNotFoundException("This Delivery does not exist!");
        }
    }
}
