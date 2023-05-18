package tie.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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
        when(adminRepository.findById(dummyAdmin1.getId())).thenReturn(Optional.ofNullable(dummyAdmin1));

        Admin returnedAdmin = adminService.getAdminById(dummyAdmin1.getId());

        assertEquals(dummyAdmin1, returnedAdmin);
        verify(adminRepository, times(1)).findById(dummyAdmin1.getId());
    }

    @Test
    void whenGetAdminByInvalidId_thenReturnNull() {
        Long id = 200L;

        when(adminRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Admin returnedAdmin = adminService.getAdminById(id);

        assertNull(returnedAdmin);
        verify(adminRepository, times(1)).findById(id);
    }

    @Test
    void whenGetAdminByEmail_thenReturnAdmins() {
        List<Admin> listedDummyAdmin1 = new ArrayList<Admin>(Arrays.asList(dummyAdmin1));
        when(adminRepository.findByEmail(dummyAdmin1.getEmail())).thenReturn(listedDummyAdmin1);

        List<Admin> returnedAdmins = adminService.getAdminByEmail(dummyAdmin1.getEmail());

        assertEquals(listedDummyAdmin1, returnedAdmins);
        verify(adminRepository, times(1)).findByEmail(dummyAdmin1.getEmail());
    }

    @Test
    void whenGetAdminByInvalidEmail_thenReturnEmptyList() {
        String invalidEmail = "some email";

        when(adminRepository.findByEmail(invalidEmail)).thenReturn(new ArrayList<>());

        List<Admin> returnedAdmins = adminService.getAdminByEmail(invalidEmail);

        assertThat(returnedAdmins.isEmpty());
        verify(adminRepository, times(1)).findByEmail(invalidEmail);
    }
}
