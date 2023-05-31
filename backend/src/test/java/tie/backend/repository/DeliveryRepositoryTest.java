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

import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.model.Store;

@DataJpaTest
class DeliveryRepositoryTest {
    
    @Autowired 
    private TestEntityManager testEntityManager;

    @Autowired
    private DeliveryRepository deliveryRepository;

    private ArrayList<Delivery> dummyDeliveries;
    private Delivery dummyDelivery1;
    private Delivery dummyDelivery2;
    private PickupPoint dummyPickupPoint;
    private Store dummyStore;

    @BeforeEach
    void setUp(){
        dummyDeliveries = new ArrayList<>();
        dummyPickupPoint = new PickupPoint();
        dummyStore = new Store();
        dummyDelivery1 = new Delivery("userName1", "userEmail1", 20L, dummyPickupPoint, dummyStore);
        dummyDelivery2 = new Delivery("userName2", "userEmail2", 20L, dummyPickupPoint, dummyStore);

        dummyDeliveries.add(dummyDelivery1);
        dummyDeliveries.add(dummyDelivery2);

        testEntityManager.persistAndFlush(dummyPickupPoint);
        testEntityManager.persistAndFlush(dummyStore);
    }

    @Test
    void whenGetDeliveries_thenReturnAllDeliveries(){
        testEntityManager.persistAndFlush(dummyDelivery1);
        testEntityManager.persistAndFlush(dummyDelivery2);

        List<Delivery> returnedDeliveries = deliveryRepository.findAll();

        assertEquals(dummyDeliveries, returnedDeliveries);
    }

    @Test
    void whenGetDeliveryById_thenReturnDelivery(){
        testEntityManager.persistAndFlush(dummyDelivery1);

        Delivery returnedDelivery = deliveryRepository.findById(dummyDelivery1.getId()).orElse(null);
        
        assertEquals(dummyDelivery1, returnedDelivery);
    }

    @Test
    void whenGetDeliveryByInvalidId_thenReturnNull(){
        Long invalidId = 200L;
        Delivery returnedDelivery = deliveryRepository.findById(invalidId).orElse(null);

        assertNull(returnedDelivery);
    }

    @Test
    void whenGetDeliveryByPackageId_thenReturnDelivery(){
        testEntityManager.persistAndFlush(dummyDelivery1);
        
        Delivery returnedDelivery = deliveryRepository.findByPackageId(dummyDelivery1.getPackageId()).orElse(null);
        
        assertEquals(dummyDelivery1, returnedDelivery);
    }

    @Test
    void whenGetDeliveryByInvalidPackageId_thenReturnReturnNull(){
        Long invalidId = 200L;
        Delivery returnedDelivery = deliveryRepository.findByPackageId(invalidId).orElse(null);

        assertNull(returnedDelivery);
    }

    @Test
    void whenGetDeliveryByPickupPoint_thenReturnDelivery(){
        testEntityManager.persistAndFlush(dummyDelivery1);
        List<Delivery> dummyDelivery1List = new ArrayList<>(Arrays.asList(dummyDelivery1));
        List<Delivery> returnedDeliveries = deliveryRepository.findByPickupPoint(dummyDelivery1.getPickupPoint());
        
        assertEquals(dummyDelivery1List, returnedDeliveries);
    }

    @Test
    void whenGetDeliveryByInvalidPickupPoint_thenReturnEmptyList(){
        PickupPoint pickupPoint = new PickupPoint();
        testEntityManager.persistAndFlush(pickupPoint);
        List<Delivery> returnedDeliveries = deliveryRepository.findByPickupPoint(pickupPoint);

        assertThat(returnedDeliveries.isEmpty()).isTrue();
    }

    @Test
    void whenGetDeliveryByStore_thenReturnDelivery(){
        testEntityManager.persistAndFlush(dummyDelivery1);
        List<Delivery> dummyDelivery1List = new ArrayList<>(Arrays.asList(dummyDelivery1));
        List<Delivery> returnedDeliveries = deliveryRepository.findByStore(dummyDelivery1.getStore());
        
        assertEquals(dummyDelivery1List, returnedDeliveries);
    }

    @Test
    void whenGetDeliveryByInvalidStore_thenReturnEmptyList(){
        Store store = new Store();
        testEntityManager.persistAndFlush(store);
        List<Delivery> returnedDeliveries = deliveryRepository.findByStore(store);

        assertThat(returnedDeliveries.isEmpty()).isTrue();
    }
}
