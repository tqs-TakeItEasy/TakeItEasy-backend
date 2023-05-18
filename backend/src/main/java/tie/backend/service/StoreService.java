package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Store getStoreById(Long id) {
        Optional<Store> store = storeRepository.findById(id);
        
        if (store.isPresent()){
            return store.get();
        } else {
            return null;
        }
    }

    public List<Store> getStoreByName(String name) {
        return storeRepository.findByName(name);
    }

    public List<Store> getStoreByCompany(Company company) {
        return storeRepository.findByCompany(company);
    }

    public Store createStore(Store dummyStore1) {
        return null;
    }

}
