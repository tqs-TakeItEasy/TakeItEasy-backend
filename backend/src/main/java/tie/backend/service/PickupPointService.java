package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tie.backend.model.PickupPoint;
import tie.backend.repository.PickupPointRepository;

@Service
public class PickupPointService {

    @Autowired
    private PickupPointRepository pickupPointRepository;

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
}
