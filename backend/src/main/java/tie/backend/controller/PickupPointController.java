package tie.backend.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tie.backend.model.PickupPoint;
import tie.backend.service.PickupPointService;

@RestController
@CrossOrigin(origins={  "http://localhost:3000", "http://127.0.0.1:3000", "http://0.0.0.0:3000", 
                        "https://localhost:3000", "https://127.0.0.1:3000", "https://0.0.0.0:3000",
                        "http://localhost:5173", "http://127.0.0.1:5173", "http://0.0.0.0:5173", 
                        "https://localhost:5173", "https://127.0.0.1:5173", "https://0.0.0.0:5173"
                    })
@RequestMapping("/api/v1/pickuppoints/")
public class PickupPointController {

    @Autowired
    PickupPointService pickupPointService;

    // GET - ALL PICKUP POINTS
    @GetMapping("")
    public ResponseEntity<List<PickupPoint>> getAllDeliveries() {
        List<PickupPoint> pickupPoints = pickupPointService.getAllPickupPoints();
        return ResponseEntity.ok().body(pickupPoints);
    }

    // GET - PICKUP POINT BY STATUS
    @GetMapping("/status/{status}/")
    public ResponseEntity<List<PickupPoint>> getPickupPointsByStatus(@PathVariable(value="status") String status) {
        try {
            List<PickupPoint> pickupPoints = pickupPointService.getPickupPointsByStatus(status);
            return ResponseEntity.ok().body(pickupPoints);
        } catch(IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    // POST - NEW PICKUP POINT
    @PostMapping("add/")
    public ResponseEntity<PickupPoint> addPickupPoint(@RequestBody PickupPoint pickupPoint) {
        PickupPoint newPickupPoint = pickupPointService.addPickupPoint(pickupPoint);
        return ResponseEntity.ok().body(newPickupPoint);
    }

    // DELETE - DELETE PICKUP POINT
    @DeleteMapping("/{pickupPointId}/")
    public ResponseEntity<PickupPoint> deleteOrder(@PathVariable(value="pickupPointId") String pickupPointId) {
        if (!pickupPointId.matches("\\d+")){
            return ResponseEntity.badRequest().build();
        }
        PickupPoint pickupPoint = pickupPointService.deletePickupPointById(Long.parseLong(pickupPointId));
        return ResponseEntity.ok().body(pickupPoint);
    }
}
