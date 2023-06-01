package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tie.backend.model.Company;
import tie.backend.model.Store;
import tie.backend.repository.StoreRepository;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> getStoreById(Long id) {
        return storeRepository.findById(id);
    }

    public Optional<Store> getStoreByName(String name) {
        return storeRepository.findByName(name);
    }

    public List<Store> getStoreByCompany(Company company) {
        return storeRepository.findByCompany(company);
    }

    public Store addStore(Store store) {
        Optional<Store> storeByName = storeRepository.findByName(store.getName());
        Optional<Store> storeByEmail = storeRepository.findByEmail(store.getEmail());

        if (storeByName.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Store's name already exists");
        } else if (storeByEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Store's email already exists");
        } else {
            storeRepository.save(store);
            return store;
        }
    }
}
