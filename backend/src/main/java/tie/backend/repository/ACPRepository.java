package tie.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.ACP;

@Repository
public interface ACPRepository extends JpaRepository<ACP,Long>{
    
}
