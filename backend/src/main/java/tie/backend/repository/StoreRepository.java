package tie.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Company;
import tie.backend.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long>{

    List<Store> findByName(String name);

    List<Store> findByCompany(Company company);
    
}
