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

import tie.backend.exception.ResourceNotFoundException;
import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.model.Store;
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
    private Store dummyStore;
    
    @BeforeEach
    void setUp() {
        dummyDeliveries = new ArrayList<Delivery>();
        dummyPickupPoint = new PickupPoint();
        dummyStore = new Store();

        dummyDelivery1 = new Delivery("user1", "email1", 22L, dummyPickupPoint, dummyStore);
        dummyDelivery2 = new Delivery("user2", "email2", 24L, dummyPickupPoint, dummyStore);
        
        dummyDeliveries.add(dummyDelivery1);
        dummyDeliveries.add(dummyDelivery2);

        dummyDelivery1.setStatus("RECIEVED");

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
        when(deliveryRepository.findById(dummyDelivery1.getId())).thenReturn(Optional.of(dummyDelivery1));

        Delivery returnedDelivery = deliveryService.getDeliveryById(dummyDelivery1.getId()).orElse(null);

        assertEquals(dummyDelivery1, returnedDelivery);
        verify(deliveryRepository, times(1)).findById(dummyDelivery1.getId());
    }

    @Test
    void whenGetDeliveryByInvalidId_thenReturnNull() {
        Long id = 200L;

        when(deliveryRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Delivery returnedDelivery = deliveryService.getDeliveryById(id).orElse(null);

        assertNull(returnedDelivery);
        verify(deliveryRepository, times(1)).findById(id);
    }

    @Test
    void whenGetDeliveryByPackageId_thenReturnPackageDeliveries() {
        when(deliveryRepository.findByPackageId(dummyDelivery1.getPackageId())).thenReturn(Optional.of(dummyDelivery1));

        Delivery returnedDelivery = deliveryService.getDeliveryByPackageId(dummyDelivery1.getPackageId()).orElse(null);

        assertEquals(dummyDelivery1, returnedDelivery);
        verify(deliveryRepository, times(1)).findByPackageId(dummyDelivery1.getPackageId());
    }

    @Test
    void whenGetDeliveryByInvalidPackageId_thenReturnReturnNull() {
        Long invalidId = 200L;

        when(deliveryRepository.findByPackageId(invalidId)).thenReturn(Optional.ofNullable(null));

        Delivery returnedDelivery = deliveryService.getDeliveryByPackageId(invalidId).orElse(null);

        assertNull(returnedDelivery);
        verify(deliveryRepository, times(1)).findByPackageId(invalidId);
    }
    
    @Test
    void whenGetDeliveriesByPickupPoint_thenReturnDeliveries() {
        when(deliveryRepository.findByPickupPoint(dummyPickupPoint)).thenReturn(dummyDeliveries);

        List<Delivery> returnedDeliveries = deliveryService.getDeliveriesByPickupPoint(dummyPickupPoint);

        assertEquals(dummyDeliveries, returnedDeliveries);
        verify(deliveryRepository, times(1)).findByPickupPoint(dummyPickupPoint);
    }

    @Test
    void whenGetDeliveriesByInvalidPickupPoint_thenReturnEmptyList() {
        PickupPoint invalidPickupPoint = new PickupPoint();
        when(deliveryRepository.findByPickupPoint(invalidPickupPoint)).thenReturn(new ArrayList<>());

        List<Delivery> returnedDeliveries = deliveryService.getDeliveriesByPickupPoint(invalidPickupPoint);

        assertThat(returnedDeliveries).isEmpty();
        verify(deliveryRepository, times(1)).findByPickupPoint(invalidPickupPoint);
    }

    @Test
    void whenGetDeliveriesByStore_thenReturDeliveries() {
        when(deliveryRepository.findByStore(dummyStore)).thenReturn(dummyDeliveries);

        List<Delivery> returnedDeliveries = deliveryService.getDeliveriesByStore(dummyStore);

        assertEquals(dummyDeliveries, returnedDeliveries);
        verify(deliveryRepository, times(1)).findByStore(dummyStore);
    }

    @Test
    void whenGetDeliveriesByInvalidStore_thenReturnEmptyList() {
        Store invalidStore = new Store();
        when(deliveryRepository.findByStore(invalidStore)).thenReturn(new ArrayList<>());

        List<Delivery> returnedDeliveries = deliveryService.getDeliveriesByStore(invalidStore);

        assertThat(returnedDeliveries).isEmpty();
        verify(deliveryRepository, times(1)).findByStore(invalidStore);
    }

    @Test
    void whenAddDeliveryByValidPackageID_thenReturnDelivery() {
        when(deliveryRepository.findByPackageId(dummyDelivery1.getPackageId())).thenReturn(Optional.empty());

        Delivery newDelivery = deliveryService.addDelivery(dummyDelivery1);

        assertEquals(dummyDelivery1, newDelivery);

        verify(deliveryRepository, times(1)).findByPackageId(dummyDelivery1.getPackageId());
        verify(deliveryRepository, times(1)).save(dummyDelivery1);

    }

    @Test
    void whenAddDeliveryByInvalidPackageID_thenReturnInvalidPackageID() {
        when(deliveryRepository.findByPackageId(dummyDelivery1.getPackageId())).thenReturn(Optional.ofNullable(dummyDelivery1));

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            deliveryService.addDelivery(dummyDelivery1);
        });
        Assertions.assertEquals("This Delivery's Package ID already exists", exception.getReason());

        verify(deliveryRepository, times(1)).findByPackageId(dummyDelivery1.getPackageId());
        verify(deliveryRepository, times(0)).save(dummyDelivery1);
    }

    @Test
    void whenUpdateDeliveryByValidID_thenReturnDelivery() throws ResourceNotFoundException {
        when(deliveryRepository.findById(dummyDelivery1.getId())).thenReturn(Optional.of(dummyDelivery1));

        Delivery updatedDelivery = deliveryService.updateDeliveryStatus(dummyDelivery1);

        assertEquals(dummyDelivery1.getPackageId(), updatedDelivery.getPackageId());
        assertEquals(dummyDelivery1.getStatus(), updatedDelivery.getStatus());

        verify(deliveryRepository, times(1)).findById(dummyDelivery1.getId());
        verify(deliveryRepository, times(1)).save(dummyDelivery1);

    }

    @Test
    void whenUpdateDeliveryByInValidID_thenReturnInvalidID() throws ResourceNotFoundException {
        when(deliveryRepository.findById(dummyDelivery1.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            deliveryService.updateDeliveryStatus(dummyDelivery1);
        });
        Assertions.assertEquals("This Delivery does not exist!", exception.getMessage());

        verify(deliveryRepository, times(1)).findById(null);
        verify(deliveryRepository, times(0)).save(dummyDelivery1);
    }
}