package tie.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;
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
import tie.backend.model.PickupPoint;
import tie.backend.model.PickupPointStatus;

@DataJpaTest
class PickupPointRepositoryTest {
    
    @Autowired 
    private TestEntityManager testEntityManager;

    @Autowired
    private PickupPointRepository pickupPointRepository;

    private ArrayList<PickupPoint> dummyPickupPoints;
    private Company dummyCompany1;
    private Company dummyCompany2;
    private PickupPoint dummyPickupPoint1;
    private PickupPoint dummyPickupPoint2;

    @BeforeEach
    void setUp(){
        dummyPickupPoints = new ArrayList<>();

        dummyCompany1 = new Company("name1", "email1");
        dummyCompany2 = new Company("name2", "email2");
        
        dummyPickupPoint1 = new PickupPoint("name1", "address1", "email1", dummyCompany1);
        dummyPickupPoint2 = new PickupPoint("name2", "address2", "email2", dummyCompany2);

        dummyPickupPoints.add(dummyPickupPoint1);
        dummyPickupPoints.add(dummyPickupPoint2);

        testEntityManager.persistAndFlush(dummyCompany1);
        testEntityManager.persistAndFlush(dummyCompany2);
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

    @Test
    void whenGetCompanyByEmail_thenReturnCompany(){
        testEntityManager.persistAndFlush(dummyPickupPoint1);
        testEntityManager.persistAndFlush(dummyPickupPoint2);

        List<PickupPoint> returnedPickupPoints = pickupPointRepository.findByStatus(dummyPickupPoint1.getStatus());
        
        assertEquals(dummyPickupPoints, returnedPickupPoints);
    }

    @Test
    void whenGetCompanyByInvalidEmail_thenEmptyList(){
        PickupPointStatus invalid = PickupPointStatus.UNAVAILABLE;

        List<PickupPoint> returnedPickupPoints = pickupPointRepository.findByStatus(invalid);

        assertThat(returnedPickupPoints.isEmpty()).isTrue();
    }
}
