package tie.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Delivery;

@Repository
public interface AdminRepository extends JpaRepository<Delivery,Long>{
    
}
