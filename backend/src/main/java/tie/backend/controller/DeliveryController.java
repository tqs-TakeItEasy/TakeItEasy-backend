package tie.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tie.backend.dto.DeliveryDTO;
import tie.backend.dto.PackageDTO;
import tie.backend.exception.ResourceNotFoundException;
import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.model.Store;
import tie.backend.service.DeliveryService;
import tie.backend.service.PickupPointService;
import tie.backend.service.StoreService;

@RestController
@CrossOrigin(origins={  "http://localhost:3000", "http://127.0.0.1:3000", "http://0.0.0.0:3000", 
                        "https://localhost:3000", "https://127.0.0.1:3000", "https://0.0.0.0:3000",
                        "http://localhost:5173", "http://127.0.0.1:5173", "http://0.0.0.0:5173", 
                        "https://localhost:5173", "https://127.0.0.1:5173", "https://0.0.0.0:5173"
                    })
@RequestMapping("/api/v1/deliveries/")
public class DeliveryController{

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private PickupPointService pickupPointService;

    // GET - All Deliveries
    @GetMapping("")
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok().body(deliveries);
    }

    // GET - Deliveriy by Identifier
    @GetMapping("delivery/{deliveryId}/")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable(value="deliveryId") String deliveryId) {
        if (!deliveryId.matches("\\d+")){
            return ResponseEntity.badRequest().build();
        }
        Optional<Delivery> delivery = deliveryService.getDeliveryById(Long.valueOf(deliveryId));
        if(delivery.isPresent()){
            return ResponseEntity.ok().body(delivery.get());
        }
        return ResponseEntity.noContent().build();
    }

    // GET - Deliveries By Pickup Point Identifier
    @GetMapping("point/{pickup_point_id}/")
    public ResponseEntity<List<Delivery>> getDeliveriesByPickupPointId(@PathVariable(value="pickup_point_id") String pickup_point_id) {
        if (!pickup_point_id.matches("\\d+")){
            return ResponseEntity.badRequest().build();
        }
        Optional<PickupPoint> pickupPoint = pickupPointService.getPickupPointById(Long.parseLong(pickup_point_id));
        if(pickupPoint.isPresent()){
            List<Delivery> deliveries = deliveryService.getDeliveriesByPickupPoint(pickupPoint.get());
            return ResponseEntity.ok().body(deliveries);
        }
        return ResponseEntity.noContent().build();
    }

    // POST - NEW DELIVERY
    @PostMapping("add/")
    public ResponseEntity<PackageDTO> addDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        Optional<PickupPoint> pickupPoint = pickupPointService.getPickupPointById(deliveryDTO.getPickupPointId());
        if (pickupPoint.isPresent()) {
            Optional<Store> store = storeService.getStoreById(deliveryDTO.getStoreId());
            if (store.isPresent()) {
                Delivery delivery = new Delivery(   
                    deliveryDTO.getUserName(), 
                    deliveryDTO.getUserEmail(), 
                    deliveryDTO.getPackageId(), 
                    pickupPoint.get(),
                    store.get()
                );
                Delivery newDelivery = deliveryService.addDelivery(delivery);
                return ResponseEntity.ok().body(new PackageDTO(newDelivery.getId()));
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }

    // PUT - UPDATE DELIVERY STATUS

    @PutMapping("update/")
    public ResponseEntity<Delivery> updateDelivery(@RequestBody Delivery delivery) throws ResourceNotFoundException {
        return ResponseEntity.ok(deliveryService.updateDelivery(delivery));
    }
}
