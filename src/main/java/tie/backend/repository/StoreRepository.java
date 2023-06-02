package tie.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Company;
import tie.backend.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long>{
    Optional<Store> findByName(String name);
    Optional<Store> findByEmail(String email);
    List<Store> findByCompany(Company company);
}
