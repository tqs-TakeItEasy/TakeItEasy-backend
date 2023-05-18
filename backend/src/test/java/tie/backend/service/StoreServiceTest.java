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

import tie.backend.model.Company;
import tie.backend.model.Store;
import tie.backend.repository.StoreRepository;

class StoreServiceTest {
 
    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    private List<Store> dummyStores;
    private Store dummyStore1;
    private Store dummyStore2;
    private Company dummyCompany;

    @BeforeEach
    void setUp() {
        dummyStores = new ArrayList<Store>();
        dummyCompany = new Company();
        dummyStore1 = new Store();
        dummyStore2 = new Store();
        
        dummyStores.add(dummyStore1);
        dummyStores.add(dummyStore2);
        
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetStores_thenReturnAllStores(){
        when(storeRepository.findAll()).thenReturn(dummyStores);
        
        List<Store> returnedStore = storeService.getAllStores();
        
        assertEquals(dummyStores, returnedStore);
        verify(storeRepository, times(1)).findAll();
    }

    @Test
    void whenGetStoreById_thenReturnStore() {
        when(storeRepository.findById(dummyStore1.getId())).thenReturn(Optional.of(dummyStore1));

        Store returnedStore = storeService.getStoreById(dummyStore1.getId());

        assertEquals(dummyStore1, returnedStore);
        verify(storeRepository, times(1)).findById(dummyStore1.getId());
    }

    @Test
    void whenGetStoreByInvalidId_thenReturnNull() {
        Long id = 200L;

        when(storeRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Store returnedStore = storeService.getStoreById(id);

        assertNull(returnedStore);
        verify(storeRepository, times(1)).findById(id);
    }

    @Test
    void whenGetStoreByName_thenReturnStores() {
        List<Store> listedDummyStore1 = new ArrayList<Store>(Arrays.asList(dummyStore1));
        when(storeRepository.findByName(dummyStore1.getName())).thenReturn(listedDummyStore1);

        List<Store> returnedStores = storeService.getStoreByName(dummyStore1.getName());

        assertEquals(listedDummyStore1, returnedStores);
        verify(storeRepository, times(1)).findByName(dummyStore1.getName());
    }

    @Test
    void whenGetStoreByInvalidName_thenReturnEmptyList() {
        String invalidName = "some email";

        when(storeRepository.findByName(invalidName)).thenReturn(new ArrayList<>());

        List<Store> returnedStores = storeService.getStoreByName(invalidName);

        assertThat(returnedStores.isEmpty());
        verify(storeRepository, times(1)).findByName(invalidName);
    }

    @Test
    void whenGetStoresByCompany_thenReturnStores(){
        when(storeRepository.findByCompany(dummyCompany)).thenReturn(dummyStores);

        List<Store> returnedStores = storeService.getStoreByCompany(dummyCompany);

        assertEquals(dummyStores, returnedStores);
        verify(storeRepository, times(1)).findByCompany(dummyCompany);
    }

    @Test
    void whenGetStoreByInvalidCompany_thenReturnEmptyList(){
        Company company = new Company();

        when(storeRepository.findByCompany(company)).thenReturn(new ArrayList<>());

        List<Store> returnedStores = storeService.getStoreByCompany(company);

        assertThat(returnedStores.isEmpty());
        verify(storeRepository, times(1)).findByCompany(company);
    }

    @Test
    void whenCreateStore_thenReturnCreatedStore() {
        
        when(storeRepository.save(dummyStore1)).thenReturn(dummyStore1);

        Store returnedStore = storeService.createStore(dummyStore1);
        
        assertEquals(dummyStore1, returnedStore);
        verify(storeRepository, times(1)).save(dummyStore1);
    }
}
