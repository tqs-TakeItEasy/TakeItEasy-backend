package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tie.backend.model.ACP;
import tie.backend.model.Company;
import tie.backend.model.PickupPoint;
import tie.backend.repository.ACPRepository;

@Service
public class ACPService {

    @Autowired
    private ACPRepository acpRepository;

    public List<ACP> getAllACPs() {
        return acpRepository.findAll();
    }

    public Optional<ACP> getACPById(Long id) {
        return acpRepository.findById(id);
    }

    public Optional<ACP> getACPByEmail(String email) {
        return acpRepository.findByEmail(email);
    }

    public List<ACP> getACPByPickupPoint(PickupPoint pickupPoint) {
        return acpRepository.findByPickupPoint(pickupPoint);
    }

    public List<ACP> getACPByCompany(Company company) {
        return acpRepository.findByCompany(company);
    }
    
}
