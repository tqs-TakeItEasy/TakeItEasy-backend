package tie.backend.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tie.backend.model.Company;
import tie.backend.model.Store;
import tie.backend.service.StoreService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class StoreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StoreService storeService;
    
    private List<Store> stores;
    private Company company1;
    private Company company2;
    private Company company3;
    private Store store1;
    private Store store2;
    private Store store3;

    @BeforeEach
    void setUp() {
        stores = new ArrayList<>();
        
        company1 = new Company("name1", "email1");
        company2 = new Company("name2", "email2");
        company3 = new Company("name3", "email3");
        
        store1 = new Store("name1", "email1", company1); 
        store2 = new Store("name2", "email2", company2);
        store3 = new Store("name3", "email3", company3);

        stores.add(store1);
        stores.add(store2);
        stores.add(store3);
    }

    @Test
    void whenGetAllStores_thenReturnStoresList() throws Exception{
        
        when(storeService.getAllStores()).thenReturn(stores);

        mvc.perform(get("/api/v1/stores/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(3))))
                .andExpect(jsonPath("$[0].name", is(company1.getName())))
                .andExpect(jsonPath("$[1].name", is(company2.getName())))
                .andExpect(jsonPath("$[2].name", is(company3.getName())))
        ;
    }

    @Test
    void whenGetStoreById_thenReturnStore() throws Exception{

        Long id = 1L;
        store1.setId(id);
        
        when(storeService.getStoreById(store1.getId())).thenReturn(store1);

        mvc.perform(get("/api/v1/stores/store/" + Long.toString(id)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(store1.getId().intValue())))
                .andExpect(jsonPath("$.name", is(store1.getName())))
                .andExpect(jsonPath("$.email", is(store1.getEmail())))
        ;
    }

    @Test
    void whenGetStoreByCpId_thenReturnStore() throws Exception{

        Long id = 1L;
        store1.setId(id);
        
        when(storeService.getStoreById(store1.getId())).thenReturn(store1);

        mvc.perform(get("/api/v1/stores/store/" + Long.toString(id)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(store1.getId().intValue())))
                .andExpect(jsonPath("$.name", is(store1.getName())))
                .andExpect(jsonPath("$.email", is(store1.getEmail())))
        ;
    }

    @Test
    void whenCreateStore_thenReturnStore() throws Exception{

        Long id = 1L;
        store1.setId(id);
        
        when(storeService.createStore(store1)).thenReturn(store1);

        mvc.perform(post("/api/v1/stores/new", store1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(store1.getId().intValue())))
                .andExpect(jsonPath("$.name", is(store1.getName())))
                .andExpect(jsonPath("$.email", is(store1.getEmail())))
        ;
    }

}
