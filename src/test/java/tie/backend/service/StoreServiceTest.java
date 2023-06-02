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

        Store returnedStore = storeService.getStoreById(dummyStore1.getId()).orElse(null);

        assertEquals(dummyStore1, returnedStore);
        verify(storeRepository, times(1)).findById(dummyStore1.getId());
    }

    @Test
    void whenGetStoreByInvalidId_thenReturnNull() {
        Long id = 200L;

        when(storeRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Store returnedStore = storeService.getStoreById(id).orElse(null);

        assertNull(returnedStore);
        verify(storeRepository, times(1)).findById(id);
    }

    @Test
    void whenGetStoreByName_thenReturnStores() {
        when(storeRepository.findByName(dummyStore1.getName())).thenReturn(Optional.of(dummyStore1));

        Store returnedStore = storeService.getStoreByName(dummyStore1.getName()).orElse(null);

        assertEquals(dummyStore1, returnedStore);
        verify(storeRepository, times(1)).findByName(dummyStore1.getName());
    }

    @Test
    void whenGetStoreByInvalidName_thenReturnReturnNull() {
        String invalidName = "some email";

        when(storeRepository.findByName(invalidName)).thenReturn(Optional.ofNullable(null));

        Store returnedStore = storeService.getStoreByName(invalidName).orElse(null);

        assertNull(returnedStore);
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

        assertThat(returnedStores).isEmpty();
        verify(storeRepository, times(1)).findByCompany(company);
    }

    @Test
    void WhenAddValidStore_ThenReturnCreatedStore(){
        when(storeRepository.findByName(dummyStore1.getName())).thenReturn(Optional.ofNullable(null));
        when(storeRepository.findByEmail(dummyStore1.getEmail())).thenReturn(Optional.ofNullable(null));

        Store NewStore = storeService.addStore(dummyStore1);
        assertEquals( dummyStore1,  NewStore);

        verify(storeRepository, times(1)).findByName(dummyStore1.getName());
        verify(storeRepository, times(1)).findByEmail(dummyStore1.getEmail());
        verify(storeRepository, times(1)).save(dummyStore1);
    }

    @Test
    void WhenAddInvalidStoreName_ThenReturnInvalidName(){
        when(storeRepository.findByName(dummyStore1.getName())).thenReturn(Optional.of(dummyStore1));
        when(storeRepository.findByEmail(dummyStore1.getEmail())).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            storeService.addStore(dummyStore1);
        });
        Assertions.assertEquals("This Store's name already exists", exception.getReason());

        verify(storeRepository, times(1)).findByName(dummyStore1.getName());
        verify(storeRepository, times(1)).findByEmail(dummyStore1.getEmail());
        verify(storeRepository, times(0)).save(dummyStore1);
    }

    @Test
    void WhenAddInvalidStoreEmail_ThenReturnInvalidEmail(){
        when(storeRepository.findByName(dummyStore1.getName())).thenReturn(Optional.empty());
        when(storeRepository.findByEmail(dummyStore1.getEmail())).thenReturn(Optional.of(dummyStore1));

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            storeService.addStore(dummyStore1);
        });
        Assertions.assertEquals("This Store's email already exists", exception.getReason());

        verify(storeRepository, times(1)).findByName(dummyStore1.getName());
        verify(storeRepository, times(1)).findByEmail(dummyStore1.getEmail());
        verify(storeRepository, times(0)).save(dummyStore1);
    }
}
