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

import tie.backend.model.PickupPoint;

@DataJpaTest
class PickupPointRepositoryTest {
    
    @Autowired 
    private TestEntityManager testEntityManager;

    @Autowired
    private PickupPointRepository pickupPointRepository;

    private ArrayList<PickupPoint> dummyPickupPoints;
    private PickupPoint dummyPickupPoint1;
    private PickupPoint dummyPickupPoint2;

    @BeforeEach
    void setUp(){
        dummyPickupPoints = new ArrayList<>();
        dummyPickupPoint1 = new PickupPoint("name1", "address1", "email1");
        dummyPickupPoint2 = new PickupPoint("name2", "address2", "email2");

        dummyPickupPoints.add(dummyPickupPoint1);
        dummyPickupPoints.add(dummyPickupPoint2);
    }

    @Test
    void whenGetPickupPoints_thenReturnAllPickupPoints(){
        testEntityManager.persistAndFlush(dummyPickupPoint1);
        testEntityManager.persistAndFlush(dummyPickupPoint2);

        List<PickupPoint> returnedPickupPoints = pickupPointRepository.findAll();

        assertEquals(dummyPickupPoints, returnedPickupPoints);
    }

    @Test
    void whenGetPickupPointById_thenReturnPickupPoint(){
        testEntityManager.persistAndFlush(dummyPickupPoint1);

        PickupPoint returnedPickupPoint = pickupPointRepository.findById(dummyPickupPoint1.getId()).orElse(null);
        
        assertEquals(dummyPickupPoint1, returnedPickupPoint);
    }

    @Test
    void whenGetPickupPointByInvalidId_thenReturnNull(){
        Long invalidId = 200L;
        PickupPoint returnedPickupPoint = pickupPointRepository.findById(invalidId).orElse(null);

        assertNull(returnedPickupPoint);
    }
}
