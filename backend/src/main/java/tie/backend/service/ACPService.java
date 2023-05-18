package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tie.backend.model.ACP;
import tie.backend.repository.ACPRepository;

@Service
public class ACPService {

    @Autowired
    private ACPRepository acpRepository;

    public List<ACP> getAllACPs() {
        return acpRepository.findAll();
    }

    public ACP getACPById(Long id) {
        Optional<ACP> aCP = acpRepository.findById(id);

        if (aCP.isPresent()){
            return aCP.get();
        } else {
            return null;
        }
    }

    public List<ACP> getACPByEmail(String email) {
        return acpRepository.findByEmail(email);
    }
    
}
