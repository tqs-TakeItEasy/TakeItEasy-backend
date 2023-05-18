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

    public PickupPoint addPickupPoint(String name, String address, String email) {
        Optional<PickupPoint> newClientByName = pickupPointRepository.findByName(name);
        Optional<PickupPoint> newClientByEmail = pickupPointRepository.findByEmail(email);

        System.out.println(newClientByName);
        System.out.println(newClientByEmail);

        if (newClientByName.isPresent()){
            System.out.println("Aqui");
            throw new ResponseStatusException(HttpStatus.OK, "This Pickup Name already exists");
        } else if (newClientByEmail.isPresent()) {
            System.out.println("Aqui2");
            throw new ResponseStatusException(HttpStatus.OK, "This Pickup Email already exists");
        } else {
            System.out.println("Aqui3");
            PickupPoint newPickupPoint = new PickupPoint(name, address, email);
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
}
