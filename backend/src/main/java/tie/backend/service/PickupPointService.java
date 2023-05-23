package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import tie.backend.model.PickupPoint;
import tie.backend.model.PickupPointStatus;
import tie.backend.repository.PickupPointRepository;

@Service
public class PickupPointService {

    @Autowired
    private PickupPointRepository pickupPointRepository;

    public PickupPoint addPickupPoint(PickupPoint pickupPoint) {
        Optional<PickupPoint> pickupPointByName = pickupPointRepository.findByName(pickupPoint.getName());
        Optional<PickupPoint> pickupPointByEmail = pickupPointRepository.findByEmail(pickupPoint.getEmail());

        if (pickupPointByName.isPresent()){
            throw new ResponseStatusException(HttpStatus.OK, "This PickupPoint's name already exists");
        } else if (pickupPointByEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.OK, "This PickupPoint's email already exists");
        } else {
            pickupPointRepository.save(pickupPoint);
            return pickupPoint;
        }
    }

    public List<PickupPoint> getAllPickupPoints() {
        return pickupPointRepository.findAll();
    }

    public Optional<PickupPoint> getPickupPointById(Long id) {
        return pickupPointRepository.findById(id);
    }

    public List<PickupPoint> getPickupPointsByStatus(PickupPointStatus pickupPointStatus) {
        return pickupPointRepository.findByStatus(pickupPointStatus);
    }
}
