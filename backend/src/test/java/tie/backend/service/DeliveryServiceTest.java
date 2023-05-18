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

import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.repository.DeliveryRepository;

class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    private List<Delivery> dummyDeliveries;
    private Delivery dummyDelivery1;
    private Delivery dummyDelivery2;
    private PickupPoint dummyPickupPoint;

    @BeforeEach
    void setUp() {
        dummyDeliveries = new ArrayList<Delivery>();
        dummyPickupPoint = new PickupPoint();
        dummyDelivery1 = new Delivery("loja1", "user1", "email1", 22L, dummyPickupPoint);
        dummyDelivery2 = new Delivery("loja2", "user2", "email2", 24L, dummyPickupPoint);
        
        dummyDeliveries.add(dummyDelivery1);
        dummyDeliveries.add(dummyDelivery2);
        
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetDeliveries_thenReturnAllDeliveries(){
        when(deliveryRepository.findAll()).thenReturn(dummyDeliveries);
        
        List<Delivery> returnedDeliveries = deliveryService.getAllDeliveries();
        
        assertEquals(dummyDeliveries, returnedDeliveries);
        verify(deliveryRepository, times(1)).findAll();
    }

    @Test
    void whenGetDeliveryById_thenReturnDelivery() {
        when(deliveryRepository.findById(dummyDelivery1.getId())).thenReturn(Optional.ofNullable(dummyDelivery1));

        Delivery returnedDelivery = deliveryService.getDeliveryById(dummyDelivery1.getId());

        assertEquals(dummyDelivery1, returnedDelivery);
        verify(deliveryRepository, times(1)).findById(dummyDelivery1.getId());
    }

    @Test
    void whenGetDeliveryByInvalidId_thenReturnNull() {
        Long id = 200L;

        when(deliveryRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Delivery returnedDelivery = deliveryService.getDeliveryById(id);

        assertNull(returnedDelivery);
        verify(deliveryRepository, times(1)).findById(id);
    }

    @Test
    void whenGetPickupPointDeliveries_thenReturnAllPickupPointDeliveries() {
        when(deliveryRepository.findByPickupPoint(dummyPickupPoint)).thenReturn(dummyDeliveries);

        List<Delivery> returnedDeliveries = deliveryService.getDeliveriesByPickupPoint(dummyPickupPoint);

        assertEquals(dummyDeliveries, returnedDeliveries);
        verify(deliveryRepository, times(1)).findByPickupPoint(dummyPickupPoint);
    }

    @Test
    void whenGetInvalidPickupPointDeliveries_thenReturnEmptyList() {
        PickupPoint invalidPickupPoint = new PickupPoint();
        when(deliveryRepository.findByPickupPoint(invalidPickupPoint)).thenReturn(new ArrayList<>());

        List<Delivery> returnedDeliveries = deliveryService.getDeliveriesByPickupPoint(invalidPickupPoint);

        assertThat(returnedDeliveries.isEmpty());
        verify(deliveryRepository, times(1)).findByPickupPoint(invalidPickupPoint);
    }

    @Test
    void whenGetDeliveryByPackageId_thenReturnPackageDeliveries() {
        List<Delivery> listedDummyDelivery1 = new ArrayList<Delivery>(Arrays.asList(dummyDelivery1));
        when(deliveryRepository.findByPackageId(dummyDelivery1.getPackageId())).thenReturn(listedDummyDelivery1);

        List<Delivery> returnedDeliveries = deliveryService.getDeliveryByPackageId(dummyDelivery1.getPackageId());

        assertEquals(listedDummyDelivery1, returnedDeliveries);
        verify(deliveryRepository, times(1)).findByPackageId(dummyDelivery1.getPackageId());
    }

    @Test
    void whenGetDeliveryByInvalidPackageId_thenReturnEmptyList() {
        Long invalidId = 200L;

        when(deliveryRepository.findByPackageId(invalidId)).thenReturn(new ArrayList<>());

        List<Delivery> returnedDeliveries = deliveryService.getDeliveryByPackageId(invalidId);

        assertThat(returnedDeliveries.isEmpty());
        verify(deliveryRepository, times(1)).findByPackageId(invalidId);
    }

}
