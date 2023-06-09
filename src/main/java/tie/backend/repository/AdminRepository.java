package tie.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Admin;
import tie.backend.model.Company;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{
    Optional<Admin> findByEmail(String email);
    List<Admin> findByCompany(Company company);
}
