package tie.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.ACP;
import tie.backend.model.Company;
import tie.backend.model.PickupPoint;

@Repository
public interface ACPRepository extends JpaRepository<ACP,Long>{
    Optional<ACP> findByEmail(String email);
    List<ACP> findByPickupPoint(PickupPoint pickupPoint);
    List<ACP> findByCompany(Company company);
}
