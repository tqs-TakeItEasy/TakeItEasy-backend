package tie.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tie.backend.model.Admin;
import tie.backend.model.Company;
import tie.backend.repository.AdminRepository;

class AdminServiceTest {
    
    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private List<Admin> dummyAdmins;
    private Admin dummyAdmin1;
    private Admin dummyAdmin2;
    private Company dummyCompany;

    @BeforeEach
    void setUp() {
        dummyAdmins = new ArrayList<Admin>();
        dummyCompany = new Company();
        dummyAdmin1 = new Admin("nam1", "email1", "pwd1", dummyCompany);
        dummyAdmin2 = new Admin("nam2", "email2", "pwd2", dummyCompany);
        
        dummyAdmins.add(dummyAdmin1);
        dummyAdmins.add(dummyAdmin2);
        
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAdmins_thenReturnAllAdmins(){
        when(adminRepository.findAll()).thenReturn(dummyAdmins);
        
        List<Admin> returnedAdmin = adminService.getAllAdmins();
        
        assertEquals(dummyAdmins, returnedAdmin);
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void whenGetAdminById_thenReturnAdmin() {
        when(adminRepository.findById(dummyAdmin1.getId())).thenReturn(Optional.of(dummyAdmin1));

        Admin returnedAdmin = adminService.getAdminById(dummyAdmin1.getId()).orElse(null);

        assertEquals(dummyAdmin1, returnedAdmin);
        verify(adminRepository, times(1)).findById(dummyAdmin1.getId());
    }

    @Test
    void whenGetAdminByInvalidId_thenReturnNull() {
        Long id = 200L;

        when(adminRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Admin returnedAdmin = adminService.getAdminById(id).orElse(null);

        assertNull(returnedAdmin);
        verify(adminRepository, times(1)).findById(id);
    }

    @Test
    void whenGetAdminByEmail_thenReturnAdmins() {
        when(adminRepository.findByEmail(dummyAdmin1.getEmail())).thenReturn(Optional.of(dummyAdmin1));

        Admin returnedAdmin = adminService.getAdminByEmail(dummyAdmin1.getEmail()).orElse(null);

        assertEquals(dummyAdmin1, returnedAdmin);
        verify(adminRepository, times(1)).findByEmail(dummyAdmin1.getEmail());
    }

    @Test
    void whenGetAdminByInvalidEmail_thenReturnReturnNull() {
        String invalidEmail = "some email";

        when(adminRepository.findByEmail(invalidEmail)).thenReturn(Optional.ofNullable(null));

        Admin returnedAdmin = adminService.getAdminByEmail(invalidEmail).orElse(null);

        assertNull(returnedAdmin);
        verify(adminRepository, times(1)).findByEmail(invalidEmail);
    }

    @Test
    void whenGetAdminsByCompany_thenReturnAdmins(){
        when(adminRepository.findByCompany(dummyCompany)).thenReturn(dummyAdmins);

        List<Admin> returnedAdmins = adminService.getAdminByCompany(dummyCompany);

        assertEquals(dummyAdmins, returnedAdmins);
        verify(adminRepository, times(1)).findByCompany(dummyCompany);
    }

    @Test
    void whenGetAdminByInvalidCompany_thenReturnEmptyList(){
        Company company = new Company();

        when(adminRepository.findByCompany(company)).thenReturn(new ArrayList<>());

        List<Admin> returnedAdmins = adminService.getAdminByCompany(company);

        assertThat(returnedAdmins).isEmpty();
        verify(adminRepository, times(1)).findByCompany(company);
    }
}
