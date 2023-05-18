package tie.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tie.backend.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long>{

    List<Company> findByName(String name);
    
}
