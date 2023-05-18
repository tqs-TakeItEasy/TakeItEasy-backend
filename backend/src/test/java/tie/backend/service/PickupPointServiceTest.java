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

import tie.backend.model.PickupPoint;
import tie.backend.repository.PickupPointRepository;

class PickupPointServiceTest {
    
    @Mock
    private PickupPointRepository pickupPointRepository;

    @InjectMocks
    private PickupPointService pickupPointService;

    private List<PickupPoint> dummyPickupPoints;
    private PickupPoint dummyPickupPoint1;
    private PickupPoint dummyPickupPoint2;

    @BeforeEach
    void setUp() {
        dummyPickupPoints = new ArrayList<PickupPoint>();
        dummyPickupPoint1 = new PickupPoint("name1", "address1");
        dummyPickupPoint2 = new PickupPoint("name2", "address2");
        
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
        when(pickupPointRepository.findById(dummyPickupPoint2.getId())).thenReturn(Optional.ofNullable(dummyPickupPoint2));

        PickupPoint returnedPickupPoint = pickupPointService.getPickupPointById(dummyPickupPoint2.getId());

        assertEquals(dummyPickupPoint2, returnedPickupPoint);
        verify(pickupPointRepository, times(1)).findById(dummyPickupPoint2.getId());
    }

    @Test
    void whenGetPickupPointByInvalidId_thenReturnNull() {
        Long id = 200L;

        when(pickupPointRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        PickupPoint returnedPickupPoint = pickupPointService.getPickupPointById(id);

        assertNull(returnedPickupPoint);
        verify(pickupPointRepository, times(1)).findById(id);
    }

}
