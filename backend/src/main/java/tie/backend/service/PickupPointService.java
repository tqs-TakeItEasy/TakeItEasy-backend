package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import tie.backend.model.PickupPoint;
import tie.backend.repository.PickupPointRepository;

@Service
public class PickupPointService {

    @Autowired
    private PickupPointRepository pickupPointRepository;

    public PickupPoint addPickupPoint(PickupPoint newPickupPoint) {
        Optional<PickupPoint> newPickupPointByName = pickupPointRepository.findByName(newPickupPoint.getName());
        Optional<PickupPoint> newPickupPointByEmail = pickupPointRepository.findByEmail(newPickupPoint.getEmail());

        System.out.println(newPickupPointByName);
        System.out.println(newPickupPointByEmail);

        if (newPickupPointByName.isPresent()){
            throw new ResponseStatusException(HttpStatus.OK, "This Pickup Name already exists");
        } else if (newPickupPointByEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.OK, "This Pickup Email already exists");
        } else {
            pickupPointRepository.save(newPickupPoint);
            return newPickupPoint;
        }
    }

    public List<PickupPoint> getAllPickupPoints() {
        return pickupPointRepository.findAll();
    }

    public PickupPoint getPickupPointById(Long id) {
        Optional<PickupPoint> pickupPoint = pickupPointRepository.findById(id);

        if (pickupPoint.isPresent()){
            return pickupPoint.get();
        } else {
            return null;
        }
    }

    public Long getNextId() {
        long max_id = 0;
        for (PickupPoint pickupPoint : getAllPickupPoints()) {
            long id = pickupPoint.getId();
            if (id > max_id) {
                max_id = id;
            }
        }
        return max_id+1;
    }
}
