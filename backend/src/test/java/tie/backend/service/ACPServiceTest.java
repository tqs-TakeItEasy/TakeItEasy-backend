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

import tie.backend.model.ACP;
import tie.backend.model.Company;
import tie.backend.model.PickupPoint;
import tie.backend.repository.ACPRepository;

class ACPServiceTest {
 
    @Mock
    private ACPRepository aCPRepository;

    @InjectMocks
    private ACPService aCPService;

    private List<ACP> dummyACPs;
    private ACP dummyACP1;
    private ACP dummyACP2;
    private Company dummyCompany;
    private PickupPoint pickupPoint;

    @BeforeEach
    void setUp() {
        dummyACPs = new ArrayList<ACP>();
        dummyCompany = new Company();
        dummyACP1 = new ACP("nam1", "email1", "pwd1", dummyCompany, pickupPoint);
        dummyACP2 = new ACP("nam2", "email2", "pwd2", dummyCompany, pickupPoint);
        
        dummyACPs.add(dummyACP1);
        dummyACPs.add(dummyACP2);
        
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetACPs_thenReturnAllACPs(){
        when(aCPRepository.findAll()).thenReturn(dummyACPs);
        
        List<ACP> returnedACP = aCPService.getAllACPs();
        
        assertEquals(dummyACPs, returnedACP);
        verify(aCPRepository, times(1)).findAll();
    }

    @Test
    void whenGetACPById_thenReturnACP(){
        when(aCPRepository.findById(dummyACP1.getId())).thenReturn(Optional.of(dummyACP1));

        ACP returnedACP = aCPService.getACPById(dummyACP1.getId());

        assertEquals(dummyACP1, returnedACP);
        verify(aCPRepository, times(1)).findById(dummyACP1.getId());
    }

    @Test
    void whenGetACPByInvalidId_thenReturnNull(){
        Long id = 200L;

        when(aCPRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        ACP returnedACP = aCPService.getACPById(id);

        assertNull(returnedACP);
        verify(aCPRepository, times(1)).findById(id);
    }

    @Test
    void whenGetACPByEmail_thenReturnACPs(){
        List<ACP> listedDummyACP1 = new ArrayList<ACP>(Arrays.asList(dummyACP1));
        when(aCPRepository.findByEmail(dummyACP1.getEmail())).thenReturn(listedDummyACP1);

        List<ACP> returnedACPs = aCPService.getACPByEmail(dummyACP1.getEmail());

        assertEquals(listedDummyACP1, returnedACPs);
        verify(aCPRepository, times(1)).findByEmail(dummyACP1.getEmail());
    }

    @Test
    void whenGetACPByInvalidEmail_thenReturnEmptyList(){
        String invalidEmail = "some email";

        when(aCPRepository.findByEmail(invalidEmail)).thenReturn(new ArrayList<>());

        List<ACP> returnedACPs = aCPService.getACPByEmail(invalidEmail);

        assertThat(returnedACPs.isEmpty());
        verify(aCPRepository, times(1)).findByEmail(invalidEmail);
    }

    @Test
    void whenGetACPsByPickupPoint_thenReturnACPs(){
        when(aCPRepository.findByPickupPoint(pickupPoint)).thenReturn(dummyACPs);

        List<ACP> returnedACPs = aCPService.getACPByPickupPoint(pickupPoint);

        assertEquals(dummyACPs, returnedACPs);
        verify(aCPRepository, times(1)).findByPickupPoint(pickupPoint);
    }

    @Test
    void whenGetACPByInvalidPickupPoint_thenReturnEmptyList(){
        PickupPoint invalidPickupPoint = new PickupPoint();

        when(aCPRepository.findByPickupPoint(invalidPickupPoint)).thenReturn(new ArrayList<>());

        List<ACP> returnedACPs = aCPService.getACPByPickupPoint(invalidPickupPoint);

        assertThat(returnedACPs.isEmpty());
        verify(aCPRepository, times(1)).findByPickupPoint(invalidPickupPoint);
    }

    @Test
    void whenGetACPsByCompany_thenReturnACPs(){
        when(aCPRepository.findByCompany(dummyCompany)).thenReturn(dummyACPs);

        List<ACP> returnedACPs = aCPService.getACPByCompany(dummyCompany);

        assertEquals(dummyACPs, returnedACPs);
        verify(aCPRepository, times(1)).findByCompany(dummyCompany);
    }

    @Test
    void whenGetACPByInvalidCompany_thenReturnEmptyList(){
        Company company = new Company();

        when(aCPRepository.findByCompany(company)).thenReturn(new ArrayList<>());

        List<ACP> returnedACPs = aCPService.getACPByCompany(company);

        assertThat(returnedACPs.isEmpty());
        verify(aCPRepository, times(1)).findByCompany(company);
    }
}
