package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tie.backend.model.Company;
import tie.backend.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        
        if (company.isPresent()){
            return company.get();
        } else {
            return null;
        }
    }

    public List<Company> getCompanyByName(String name) {
        return companyRepository.findByName(name);
    }
    
}
