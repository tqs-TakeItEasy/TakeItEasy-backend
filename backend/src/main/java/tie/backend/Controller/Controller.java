package tie.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tie.backend.model.Delivery;
import tie.backend.service.DeliveryService;
import tie.backend.service.PickupPointService;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://127.0.0.1:3000"})
public class Controller{
    @Autowired
    DeliveryService deliveryService;

    // Get all deliveries
    @GetMapping("/allDeliveries")
    public ArrayList<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    // Get all deliveries
    @GetMapping("/pickUpPointDeliveries")
    public ArrayList<Delivery> getAllDeliveries(@RequestParam String pickUpPointId) {
        return deliveryService.getDeliveriesByPickupPointId(Long.valueOf(pickUpPointId));
    }

    // Get all deliveries
    @GetMapping("/Delivery")
    public Object getDelivery(@RequestParam String deliveryId) {
        return deliveryService.getDeliveryById(Long.valueOf(deliveryId));
    }
}
