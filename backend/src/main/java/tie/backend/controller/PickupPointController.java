package tie.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tie.backend.model.PickupPoint;
import tie.backend.service.PickupPointService;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://127.0.0.1:3000"})
@RequestMapping("/api/v1/pickuppoints/")
public class PickupPointController {

    @Autowired
    PickupPointService pickupPointService;

    // POST - NEW PICKUP POINT
    @PostMapping("add/")
    public ResponseEntity<PickupPoint> addPickupPoint(@RequestBody PickupPoint pickupPoint) {
        PickupPoint newPickupPoint = pickupPointService.addPickupPoint(pickupPoint);
        return ResponseEntity.ok().body(newPickupPoint);
    }
}
