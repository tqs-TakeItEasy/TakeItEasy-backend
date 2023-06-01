package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tie.backend.model.Company;
import tie.backend.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Optional<Company> getCompanyByName(String name) {
        return companyRepository.findByName(name);
    }

    public Company addCompany(Company company) {
        Optional<Company> companyByName = companyRepository.findByName(company.getName());
        Optional<Company> companyByEmail = companyRepository.findByEmail(company.getEmail());

        if (companyByName.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "This Company's name already exists");
        } else if (companyByEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "This Company's email already exists");
        } else {
            companyRepository.save(company);
            return company;
        }
    }
}