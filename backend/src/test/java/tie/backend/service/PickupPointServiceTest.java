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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import tie.backend.model.Company;
import tie.backend.model.PickupPoint;
import tie.backend.repository.PickupPointRepository;

class PickupPointServiceTest {
    
    @Mock
    private PickupPointRepository pickupPointRepository;

    @InjectMocks
    private PickupPointService pickupPointService;

    private List<PickupPoint> dummyPickupPoints;
    private Company dummyCompany1;
    private Company dummyCompany2;
    private PickupPoint dummyPickupPoint1;
    private PickupPoint dummyPickupPoint2;

    @BeforeEach
    void setUp() {
        dummyPickupPoints = new ArrayList<PickupPoint>();
        dummyPickupPoint1 = new PickupPoint("name1", "address1", "email1", dummyCompany1);
        dummyPickupPoint2 = new PickupPoint("name2", "address2", "email2", dummyCompany2);
        
        dummyPickupPoints.add(dummyPickupPoint1);
        dummyPickupPoints.add(dummyPickupPoint2);
        
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetPickupPoints_thenReturnAllPickupPoints(){
        when(pickupPointRepository.findAll()).thenReturn(dummyPickupPoints);
        
        List<PickupPoint> returnedPickupPoints = pickupPointService.getAllPickupPoints();
        
        assertEquals(dummyPickupPoints, returnedPickupPoints);
        verify(pickupPointRepository, times(1)).findAll();
    }

    @Test
    void whenGetPickupPointById_thenReturnPickupPoint() {
        when(pickupPointRepository.findById(dummyPickupPoint2.getId())).thenReturn(Optional.of(dummyPickupPoint2));

        PickupPoint returnedPickupPoint = pickupPointService.getPickupPointById(dummyPickupPoint2.getId()).orElse(null);

        assertEquals(dummyPickupPoint2, returnedPickupPoint);
        verify(pickupPointRepository, times(1)).findById(dummyPickupPoint2.getId());
    }

    @Test
    void whenGetPickupPointByInvalidId_thenReturnNull() {
        Long id = 200L;

        when(pickupPointRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        PickupPoint returnedPickupPoint = pickupPointService.getPickupPointById(id).orElse(null);

        assertNull(returnedPickupPoint);
        verify(pickupPointRepository, times(1)).findById(id);
    }

    @Test
    void whenGetPickupPointsByStatus_thenReturnPickupPoint() {
        when(pickupPointRepository.findByStatus(dummyPickupPoint1.getStatus())).thenReturn(dummyPickupPoints);

        List<PickupPoint> returnedPickupPoints = pickupPointService.getPickupPointsByStatus(dummyPickupPoint1.getStatus());

        assertEquals(dummyPickupPoints, returnedPickupPoints);
        verify(pickupPointRepository, times(1)).findByStatus(dummyPickupPoint1.getStatus());
    }

    @Test
    void whenGetPickupPointsByInvalidStatus_thenReturnNull() {
        when(pickupPointRepository.findByStatus("UNAVAILABLE")).thenReturn(new ArrayList<>());

        List<PickupPoint> returnedPickupPoints = pickupPointService.getPickupPointsByStatus("UNAVAILABLE");

        assertThat(returnedPickupPoints).isEmpty();
        verify(pickupPointRepository, times(1)).findByStatus("UNAVAILABLE");
    }

    @Test
    void whenAddValidPickupPoint_ThenReturnCreatedPickupPoint(){
        when(pickupPointRepository.findByName(dummyPickupPoint1.getName())).thenReturn(Optional.ofNullable(null));
        when(pickupPointRepository.findByEmail(dummyPickupPoint1.getEmail())).thenReturn(Optional.ofNullable(null));

        PickupPoint NewPickupPoint = pickupPointService.addPickupPoint(dummyPickupPoint1);
        assertEquals( dummyPickupPoint1,  NewPickupPoint);

        verify(pickupPointRepository, times(1)).findByName(dummyPickupPoint1.getName());
        verify(pickupPointRepository, times(1)).findByEmail(dummyPickupPoint1.getEmail());
        verify(pickupPointRepository, times(1)).save(dummyPickupPoint1);
    }

    @Test
    void whenAddInvalidPickupPointName_ThenReturnInvalidName(){
        when(pickupPointRepository.findByName(dummyPickupPoint1.getName())).thenReturn(Optional.of(dummyPickupPoint1));
        when(pickupPointRepository.findByEmail(dummyPickupPoint1.getEmail())).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            pickupPointService.addPickupPoint(dummyPickupPoint1);
        });
        
        assertEquals("This PickupPoint's name already exists", exception.getReason());

        verify(pickupPointRepository, times(1)).findByName(dummyPickupPoint1.getName());
        verify(pickupPointRepository, times(1)).findByEmail(dummyPickupPoint1.getEmail());
        verify(pickupPointRepository, times(0)).save(dummyPickupPoint1);
    }

    @Test
    void whenAddInvalidPickupPointEmail_ThenReturnInvalidEmail(){
        when(pickupPointRepository.findByName(dummyPickupPoint1.getName())).thenReturn(Optional.empty());
        when(pickupPointRepository.findByEmail(dummyPickupPoint1.getEmail())).thenReturn(Optional.of(dummyPickupPoint1));

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            pickupPointService.addPickupPoint(dummyPickupPoint1);
        });
        
        assertEquals("This PickupPoint's email already exists", exception.getReason());

        verify(pickupPointRepository, times(1)).findByName(dummyPickupPoint1.getName());
        verify(pickupPointRepository, times(1)).findByEmail(dummyPickupPoint1.getEmail());
        verify(pickupPointRepository, times(0)).save(dummyPickupPoint1);
    }

    @Test
    void whenDeletePickupPoint_thenReturnPickupPoint() {
        dummyPickupPoint1.setId(10L);

        when(pickupPointRepository.findById(dummyPickupPoint1.getId())).thenReturn(Optional.of(dummyPickupPoint1));

        PickupPoint pickupPoint = pickupPointService.deletePickupPointById(dummyPickupPoint1.getId());

        assertEquals(dummyPickupPoint1, pickupPoint);

        verify(pickupPointRepository, times(1)).findById(dummyPickupPoint1.getId());
        verify(pickupPointRepository, times(1)).deleteById(dummyPickupPoint1.getId());
    }

    @Test
    void whenDeleteInvalidPickupPoint_thenReturnPickupPoint() {
        Long invalid = 200L;

        when(pickupPointRepository.findById(invalid)).thenReturn(Optional.ofNullable(null));

        PickupPoint pickupPoint = pickupPointService.deletePickupPointById(invalid);

        assertNull(pickupPoint);

        verify(pickupPointRepository, times(1)).findById(invalid);
        verify(pickupPointRepository, times(0)).deleteById(invalid);
    }
}
