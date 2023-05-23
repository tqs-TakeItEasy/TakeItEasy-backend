package tie.backend.service;

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

import tie.backend.model.Company;
import tie.backend.repository.CompanyRepository;

class CompanyServiceTest {
    
    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    private List<Company> dummyCompanies;
    private Company dummyCompany1;
    private Company dummyCompany2;

    @BeforeEach
    void setUp() {
        dummyCompanies = new ArrayList<Company>();
        dummyCompany1 = new Company("nam1", "email1");
        dummyCompany2 = new Company("nam2", "email2");
        
        dummyCompanies.add(dummyCompany1);
        dummyCompanies.add(dummyCompany2);
        
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetCompanies_thenReturnAllCompanies(){
        when(companyRepository.findAll()).thenReturn(dummyCompanies);
        
        List<Company> returnedCompany = companyService.getAllCompanies();
        
        assertEquals(dummyCompanies, returnedCompany);
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void whenGetCompanyById_thenReturnCompany() {
        when(companyRepository.findById(dummyCompany1.getId())).thenReturn(Optional.of(dummyCompany1));

        Company returnedCompany = companyService.getCompanyById(dummyCompany1.getId()).orElse(null);

        assertEquals(dummyCompany1, returnedCompany);
        verify(companyRepository, times(1)).findById(dummyCompany1.getId());
    }

    @Test
    void whenGetCompanyByInvalidId_thenReturnNull() {
        Long id = 200L;

        when(companyRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Company returnedCompany = companyService.getCompanyById(id).orElse(null);

        assertNull(returnedCompany);
        verify(companyRepository, times(1)).findById(id);
    }

    @Test
    void whenGetCompanyByName_thenReturnCompanies() {
        when(companyRepository.findByName(dummyCompany1.getName())).thenReturn(Optional.of(dummyCompany1));

        Company returnedCompany = companyService.getCompanyByName(dummyCompany1.getName()).orElse(null);

        assertEquals(dummyCompany1, returnedCompany);
        verify(companyRepository, times(1)).findByName(dummyCompany1.getName());
    }

    @Test
    void whenGetCompanyByInvalidName_thenReturnReturnNull() {
        String invalidName = "some email";

        when(companyRepository.findByName(invalidName)).thenReturn(Optional.ofNullable(null));

        Company returnedCompany = companyService.getCompanyByName(invalidName).orElse(null);

        assertNull(returnedCompany);
        verify(companyRepository, times(1)).findByName(invalidName);
    }
}
