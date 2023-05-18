package tie.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.ACP;

@Repository
public interface ACPRepository extends JpaRepository<ACP,Long>{

    List<ACP> findByEmail(String email);
    
}
