package tie.backend.controller;


import java.util.List;
import java.util.Optional;

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

import tie.backend.dto.PickupPointDTO;
import tie.backend.model.Company;
import tie.backend.model.PickupPoint;
import tie.backend.service.CompanyService;
import tie.backend.service.PickupPointService;

@RestController
@CrossOrigin(origins={"*"})
@RequestMapping("/api/v1/pickuppoints/")
public class PickupPointController {

    @Autowired
    private PickupPointService pickupPointService;

    @Autowired
    private CompanyService companyService;

    // GET - ALL PICKUP POINTS
    @GetMapping("")
    public ResponseEntity<List<PickupPoint>> getAllPickupPoints() {
        List<PickupPoint> pickupPoints = pickupPointService.getAllPickupPoints();
        return ResponseEntity.ok().body(pickupPoints);
    }

    // GET - PICKUP POINT BY STATUS
    @GetMapping("status/{status}/")
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
    public ResponseEntity<PickupPoint> addPickupPoint(@RequestBody PickupPointDTO pickupPointDTO) {
        Optional<Company> company = companyService.getCompanyById(pickupPointDTO.getCompanyId());
        if (company.isPresent()) {
            PickupPoint pickupPoint = new PickupPoint(   
                pickupPointDTO.getName(), 
                pickupPointDTO.getAddress(), 
                pickupPointDTO.getEmail(), 
                company.get()
            );
            PickupPoint newPickupPoint = pickupPointService.addPickupPoint(pickupPoint);
            return ResponseEntity.ok().body(newPickupPoint);
        }
        return ResponseEntity.badRequest().build();
    }

    // DELETE - DELETE PICKUP POINT
    @DeleteMapping("{pickupPointId}/")
    public ResponseEntity<PickupPoint> deletePickupPoint(@PathVariable(value="pickupPointId") String pickupPointId) {
        if (!pickupPointId.matches("\\d+")){
            return ResponseEntity.badRequest().build();
        }
        PickupPoint pickupPoint = pickupPointService.deletePickupPointById(Long.parseLong(pickupPointId));
        return ResponseEntity.ok().body(pickupPoint);
    }
}
