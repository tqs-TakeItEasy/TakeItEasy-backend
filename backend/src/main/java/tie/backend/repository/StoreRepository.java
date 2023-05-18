package tie.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long>{
    
}
