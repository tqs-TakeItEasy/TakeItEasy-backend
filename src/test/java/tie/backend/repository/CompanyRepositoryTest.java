package tie.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tie.backend.model.Company;

@DataJpaTest
class CompanyRepositoryTest {
    
    @Autowired 
    private TestEntityManager testEntityManager;

    @Autowired
    private CompanyRepository companyRepository;

    private List<Company> dummyCompanies;
    private Company dummyCompany1;
    private Company dummyCompany2;
    
    @BeforeEach
    void setUp(){
        companyRepository.deleteAll();
        
        dummyCompanies = new ArrayList<>();
        dummyCompany1 = new Company("name1", "email1");
        dummyCompany2 = new Company("name2", "email2");

        dummyCompanies.add(dummyCompany1);
        dummyCompanies.add(dummyCompany2);
    }

    @Test
    void whenGetCompanies_thenReturnAllCompanies(){
        testEntityManager.persistAndFlush(dummyCompany1);
        testEntityManager.persistAndFlush(dummyCompany2);

        List<Company> returnedCompanies = companyRepository.findAll();

        assertEquals(dummyCompanies, returnedCompanies);
    }

    @Test
    void whenGetCompanyById_thenReturnCompany(){
        testEntityManager.persistAndFlush(dummyCompany1);

        Company returnedCompany = companyRepository.findById(dummyCompany1.getId()).orElse(null);
        
        assertEquals(dummyCompany1, returnedCompany);
    }

    @Test
    void whenGetCompanyByInvalidId_thenReturnNull(){
        Long invalidId = 200L;
        Company returnedCompany = companyRepository.findById(invalidId).orElse(null);

        assertNull(returnedCompany);
    }

    @Test
    void whenGetCompanyByName_thenReturnCompany(){
        testEntityManager.persistAndFlush(dummyCompany1);

        Company returnedCompany = companyRepository.findByName(dummyCompany1.getName()).orElse(null);
        
        assertEquals(dummyCompany1, returnedCompany);
    }

    @Test
    void whenGetCompanyByInvalidName_thenEmptyList(){
        String invalidName = "some email";
        Company returnedCompany = companyRepository.findByName(invalidName).orElse(null);

        assertNull(returnedCompany);
    }

    @Test
    void whenGetCompanyByEmail_thenReturnCompany(){
        testEntityManager.persistAndFlush(dummyCompany1);

        Company returnedCompany = companyRepository.findByEmail(dummyCompany1.getEmail()).orElse(null);
        
        assertEquals(dummyCompany1, returnedCompany);
    }

    @Test
    void whenGetCompanyByInvalidEmail_thenEmptyList(){
        String invalidName = "some email";
        Company returnedCompany = companyRepository.findByEmail(invalidName).orElse(null);

        assertNull(returnedCompany);
    }
}
