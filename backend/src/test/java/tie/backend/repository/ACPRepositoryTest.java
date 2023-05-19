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

import tie.backend.model.ACP;
import tie.backend.model.Company;
import tie.backend.model.PickupPoint;

@DataJpaTest
class ACPRepositoryTest {
    
    @Autowired 
    private TestEntityManager testEntityManager;

    @Autowired
    private ACPRepository acpRepository;

    private ArrayList<ACP> dummyACPs;
    private ACP dummyACP1;
    private ACP dummyACP2;
    private Company dummyCompany;
    private PickupPoint dummyPickupPoint;

    @BeforeEach
    void setUp(){
        dummyACPs = new ArrayList<>();

        dummyCompany = new Company();

        dummyACP1 = new ACP("name1", "email1", "pwd1", dummyCompany, dummyPickupPoint);
        dummyACP2 = new ACP("name2", "email2", "pwd2", dummyCompany, dummyPickupPoint);

        dummyACPs.add(dummyACP1);
        dummyACPs.add(dummyACP2);

        testEntityManager.persistAndFlush(dummyCompany);
    }

    @Test
    void whenGetACPs_thenReturnAllACPs(){
        testEntityManager.persistAndFlush(dummyACP1);
        testEntityManager.persistAndFlush(dummyACP2);

        List<ACP> returnedACPs = acpRepository.findAll();

        assertEquals(dummyACPs, returnedACPs);
    }

    @Test
    void whenGetACPById_thenReturnACP(){
        testEntityManager.persistAndFlush(dummyACP1);

        ACP returnedACP = acpRepository.findById(dummyACP1.getId()).orElse(null);
        
        assertEquals(dummyACP1, returnedACP);
    }

    @Test
    void whenGetACPByInvalidId_thenReturnNull(){
        Long invalidId = 200L;
        ACP returnedACP = acpRepository.findById(invalidId).orElse(null);

        assertNull(returnedACP);
    }

    @Test
    void whenGetACPByEmail_thenReturnACP(){
        testEntityManager.persistAndFlush(dummyACP1);

        ACP returnedACP = acpRepository.findByEmail(dummyACP1.getEmail()).orElse(null);
        
        assertEquals(dummyACP1, returnedACP);
    }

    @Test
    void whenGetACPByInvalidEmail_thenReturnNull(){
        String invalidEmail = "some email";
        ACP returnedACP = acpRepository.findByEmail(invalidEmail).orElse(null);

        assertNull(returnedACP);
    }

    @Test
    void whenGetACPByCompany_thenReturnACP(){
        testEntityManager.persistAndFlush(dummyACP1);
        
        List<ACP> dummyACP1List = new ArrayList<>(Arrays.asList(dummyACP1));
        List<ACP> returnedACPs = acpRepository.findByCompany(dummyACP1.getCompany());
        
        assertEquals(dummyACP1List, returnedACPs);
    }

    @Test
    void whenGetACPByInvalidCompany_thenReturnNull(){
        Company company = new Company();
        testEntityManager.persistAndFlush(company);

        List<ACP> returnedACPs = acpRepository.findByCompany(company);

        assertThat(returnedACPs.isEmpty());
    }

    @Test
    void whenGetACPByPickupPoint_thenReturnACP(){
        testEntityManager.persistAndFlush(dummyACP1);
        
        List<ACP> dummyACP1List = new ArrayList<>(Arrays.asList(dummyACP1));
        List<ACP> returnedACPs = acpRepository.findByPickupPoint(dummyACP1.getPickupPoint());
        
        assertEquals(dummyACP1List, returnedACPs);
    }

    @Test
    void whenGetACPByPickupPoint_thenEmptyList(){
        PickupPoint pickupPoint = new PickupPoint();
        
        testEntityManager.persistAndFlush(pickupPoint);

        List<ACP> returnedACPs = acpRepository.findByPickupPoint(pickupPoint);

        assertThat(returnedACPs.isEmpty());
    }
}
