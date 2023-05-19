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

import tie.backend.model.Store;
import tie.backend.service.StoreService;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://127.0.0.1:3000"})
@RequestMapping("/api/v1/stores/")
public class StoreController {
    
    @Autowired
    private StoreService storeService;

    // GET - All Stores
    @GetMapping("")
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return ResponseEntity.ok().body(stores);
    }

    // GET - Store by Identifier
    @GetMapping("store/{store_id}/")
    public ResponseEntity<Store> getStoreById(@PathVariable(value="store_id") String storeId) {
        if (!storeId.matches("\\d+")){
            return ResponseEntity.badRequest().build();
        }
        Optional<Store> store = storeService.getStoreById(Long.valueOf(storeId));
        if(store.isPresent()){
            return ResponseEntity.ok().body(store.get());
        }
        return ResponseEntity.noContent().build();
    }

    // POST - Add New Pickup Point
    @PostMapping("add/")
    public ResponseEntity<Store> addStore(@RequestBody Store pickupPoint) {
        Store newStore = storeService.addStore(pickupPoint);
        return ResponseEntity.ok().body(newStore);
    }
}
