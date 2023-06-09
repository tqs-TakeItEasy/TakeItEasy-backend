package tie.backend.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tie.backend.dto.StoreDTO;
import tie.backend.model.Company;
import tie.backend.model.Store;
import tie.backend.service.CompanyService;
import tie.backend.service.StoreService;

@RestController
@CrossOrigin(origins={  "http://localhost:3000", "http://127.0.0.1:3000", "http://0.0.0.0:3000", 
                        "https://localhost:3000", "https://127.0.0.1:3000", "https://0.0.0.0:3000",
                        "http://localhost:5173", "http://127.0.0.1:5173", "http://0.0.0.0:5173", 
                        "https://localhost:5173", "https://127.0.0.1:5173", "https://0.0.0.0:5173"
                    })
@RequestMapping("/api/v1/stores/")
public class StoreController {
    
    @Autowired
    private StoreService storeService;

    @Autowired
    private CompanyService companyService;

    // GET - All Stores
    @GetMapping("")
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return ResponseEntity.ok().body(stores);
    }

    // GET - Store by Identifier
    @GetMapping("store/{store_id}/")
    public ResponseEntity<Store> getStoreById(@PathVariable(value="store_id") String store_id) {
        if (!store_id.matches("\\d+")){
            return ResponseEntity.badRequest().build();
        }
        Optional<Store> store = storeService.getStoreById(Long.valueOf(store_id));
        if(store.isPresent()){
            return ResponseEntity.ok().body(store.get());
        }
        return ResponseEntity.noContent().build();
    }

    // POST - NEW STORE
    @PostMapping("add/")
    public ResponseEntity<Store> addStore(@RequestBody StoreDTO storeDTO) {
        Optional<Company> company = companyService.getCompanyById(storeDTO.getCompanyId());
        if (company.isPresent()) {
            Store store = new Store(   
                storeDTO.getName(), 
                storeDTO.getEmail(), 
                company.get()
            );
            Store newStore = storeService.addStore(store);
            return ResponseEntity.ok().body(newStore);
        }
        return ResponseEntity.badRequest().build();
    }
}
