package tie.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tie.backend.service.DeliveryService;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://127.0.0.1:3000"})
public class Controller{
    @Autowired
    DeliveryService deliveryService;

    // Get all deliveries
    @GetMapping("/allDeliveries")
    public Object getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    // Get all deliveries
    @GetMapping("/pickUpPointDeliveries")
    public Object getAllDeliveries(@RequestParam String pickUpPointId) {
        return deliveryService.getPickUpPointDelivires(pickUpPointId);
    }

    // Get all deliveries
    @GetMapping("/Delivery")
    public Object getDelivery(@RequestParam String deliveryId) {
        return deliveryService.getDeliveryid(deliveryId);
    }
}
