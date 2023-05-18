package tie.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tie.backend.model.Admin;
import tie.backend.model.Company;
import tie.backend.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);

        if (admin.isPresent()){
            return admin.get();
        } else {
            return null;
        }
    }

    public List<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    public List<Admin> getAdminByCompany(Company company) {
        return adminRepository.findByCompany(company);
    }
    
}
