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

import tie.backend.model.Company;
import tie.backend.model.Store;

@DataJpaTest
class StoreRepositoryTest {
    
    @Autowired 
    private TestEntityManager testEntityManager;

    @Autowired
    private StoreRepository storeRepository;

    private ArrayList<Store> dummyStores;
    private Store dummyStore1;
    private Store dummyStore2;
    private Company dummyCompany;

    @BeforeEach
    void setUp(){
        dummyStores = new ArrayList<>();
        dummyCompany = new Company();
        dummyStore1 = new Store();
        dummyStore2 = new Store();

        dummyStores.add(dummyStore1);
        dummyStores.add(dummyStore2);

        testEntityManager.persistAndFlush(dummyCompany);
    }

    @Test
    void whenGetStores_thenReturnAllStores(){
        testEntityManager.persistAndFlush(dummyStore1);
        testEntityManager.persistAndFlush(dummyStore2);

        List<Store> returnedStores = storeRepository.findAll();

        assertEquals(dummyStores, returnedStores);
    }

    @Test
    void whenGetStoreById_thenReturnStore(){
        testEntityManager.persistAndFlush(dummyStore1);

        Store returnedStore = storeRepository.findById(dummyStore1.getId()).orElse(null);
        
        assertEquals(dummyStore1, returnedStore);
    }

    @Test
    void whenGetStoreByInvalidId_thenReturnNull(){
        Long invalidId = 200L;
        Store returnedStore = storeRepository.findById(invalidId).orElse(null);

        assertNull(returnedStore);
    }

    @Test
    void whenGetStoreByName_thenReturnStore(){
        testEntityManager.persistAndFlush(dummyStore1);

        Store returnedStore = storeRepository.findByName(dummyStore1.getName()).orElse(null);
        
        assertEquals(dummyStore1, returnedStore);
    }

    @Test
    void whenGetStoreByInvalidName_thenReturnNull(){
        String invalidName = "some email";
        Store returnedStore = storeRepository.findByName(invalidName).orElse(null);

        assertNull(returnedStore);
    }

    @Test
    void whenGetStoreByCompany_thenReturnStore(){
        testEntityManager.persistAndFlush(dummyStore1);

        List<Store> dummyStore1List = new ArrayList<>(Arrays.asList(dummyStore1));
        List<Store> returnedStores = storeRepository.findByCompany(dummyStore1.getCompany());
        
        assertEquals(dummyStore1List, returnedStores);
    }

    @Test
    void whenGetStoreByInvalidCompany_thenEmptyList(){
        Company company = new Company();
        testEntityManager.persistAndFlush(company);

        List<Store> returnedStores = storeRepository.findByCompany(company);

        assertThat(returnedStores.isEmpty());
    }
}
