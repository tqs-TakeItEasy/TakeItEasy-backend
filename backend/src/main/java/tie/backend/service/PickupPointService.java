package tie.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tie.backend.model.PickupPoint;
import tie.backend.repository.PickupPointRepository;

import java.util.Optional;

@Service
public class PickupPointService {
    @Autowired
    private PickupPointRepository repository;
}
