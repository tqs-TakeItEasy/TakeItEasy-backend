package tie.backend.Controller;

import java.util.List;

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
@CrossOrigin(origins={"http://localhost:3000", "http://127.0.0.1:3000"})
@RequestMapping("/api/v1/")
public class DeliveryController{

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    PickupPointService pickupPointService;

    // GET - All Deliveries
    @GetMapping("deliveries")
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok().body(deliveries);
    }

    // GET - Deliveriy by Identifier
    @GetMapping("/delivery/{deliveryId}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable(value="deliveryId") String deliveryId) {
        Delivery delivery = deliveryService.getDeliveryById(Long.valueOf(deliveryId));
        if (delivery == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(delivery);
    }

    // GET - Deliveries By Pickup Point Identifier
    @GetMapping("deliveries/point/{pickupPointId}")
    public ResponseEntity<List<Delivery>> gegetDeliveriesByPickupPointId(@PathVariable(value="pickupPointId") String pickupPointId) {
        if (!pickupPointId.matches("\\d+")){
            return ResponseEntity.badRequest().build();
        }
        PickupPoint pickupPoint = pickupPointService.getPickupPointById(Long.parseLong(pickupPointId));
        List<Delivery> deliveries = deliveryService.getDeliveriesByPickupPoint(pickupPoint);
        return ResponseEntity.ok().body(deliveries);
    }

}
