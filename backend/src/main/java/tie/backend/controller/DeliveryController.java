package tie.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.service.DeliveryService;
import tie.backend.service.PickupPointService;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://127.0.0.1:3000", "http://0.0.0.0:3000", "https://localhost:3000", "https://127.0.0.1:3000", "https://0.0.0.0:3000"})
@RequestMapping("/api/v1/deliveries/")
public class DeliveryController{

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    PickupPointService pickupPointService;

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
    @GetMapping("point/{pickupPointId}/")
    public ResponseEntity<List<Delivery>> getDeliveriesByPickupPointId(@PathVariable(value="pickupPointId") String pickupPointId) {
        if (!pickupPointId.matches("\\d+")){
            return ResponseEntity.badRequest().build();
        }
        Optional<PickupPoint> pickupPoint = pickupPointService.getPickupPointById(Long.parseLong(pickupPointId));
        if(pickupPoint.isPresent()){
            List<Delivery> deliveries = deliveryService.getDeliveriesByPickupPoint(pickupPoint.get());
            return ResponseEntity.ok().body(deliveries);
        }
        return ResponseEntity.noContent().build();
    }

}
