package tie.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tie.backend.model.Admin;
import tie.backend.model.Company;

@DataJpaTest
class AdminRepositoryTest {
 
    @Autowired 
    private TestEntityManager testEntityManager;

    @Autowired
    private AdminRepository adminRepository;

    private ArrayList<Admin> dummyAdmins;
    private Admin dummyAdmin1;
    private Admin dummyAdmin2;
    private Company dummyCompany;

    @BeforeEach
    void setUp(){
        dummyAdmins = new ArrayList<>();
        dummyCompany = new Company();
        dummyAdmin1 = new Admin("name1", "email1", "pwd1", dummyCompany);
        dummyAdmin2 = new Admin("name2", "email2", "pwd2", dummyCompany);

        dummyAdmins.add(dummyAdmin1);
        dummyAdmins.add(dummyAdmin2);

        testEntityManager.persistAndFlush(dummyCompany);
    }

    @Test
    void whenGetAdmins_thenReturnAllAdmins(){
        testEntityManager.persistAndFlush(dummyAdmin1);
        testEntityManager.persistAndFlush(dummyAdmin2);

        List<Admin> returnedAdmins = adminRepository.findAll();

        assertEquals(dummyAdmins, returnedAdmins);
    }

    @Test
    void whenGetAdminById_thenReturnAdmin(){
        testEntityManager.persistAndFlush(dummyAdmin1);

        Admin returnedAdmin = adminRepository.findById(dummyAdmin1.getId()).orElse(null);
        
        assertEquals(dummyAdmin1, returnedAdmin);
    }

    @Test
    void whenGetAdminByInvalidId_thenReturnNull(){
        Long invalidId = 200L;
        Admin returnedAdmin = adminRepository.findById(invalidId).orElse(null);

        assertNull(returnedAdmin);
    }

    @Test
    void whenGetAdminByEmail_thenReturnAdmin(){
        testEntityManager.persistAndFlush(dummyAdmin1);

        Admin returnedAdmin = adminRepository.findByEmail(dummyAdmin1.getEmail()).orElse(null);
        
        assertEquals(dummyAdmin1, returnedAdmin);
    }

    @Test
    void whenGetAdminByInvalidEmail_thenReturnNull(){
        String invalidEmail = "some email";
        Admin returnedAdmin = adminRepository.findByEmail(invalidEmail).orElse(null);

        assertNull(returnedAdmin);
    }

    @Test
    void whenGetAdminByCompany_thenReturnAdmin(){
        testEntityManager.persistAndFlush(dummyAdmin1);

        List<Admin> dummyAdmin1List = new ArrayList<>(Arrays.asList(dummyAdmin1));
        List<Admin> returnedAdmins = adminRepository.findByCompany(dummyAdmin1.getCompany());
        
        assertEquals(dummyAdmin1List, returnedAdmins);
    }

    @Test
    void whenGetAdminByInvalidCompany_thenEmptyList(){
        Company company = new Company();
        testEntityManager.persistAndFlush(company);

        List<Admin> returnedAdmins = adminRepository.findByCompany(company);

        assertThat(returnedAdmins.isEmpty()).isTrue();
    }
}
