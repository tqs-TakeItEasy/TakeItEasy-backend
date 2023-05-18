package tie.backend.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.Arrays;
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

import tie.backend.config.JsonUtils;
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
    
    private List<Store> dummyStores;
    private Company dummyCompany1;
    private Company dummyCompany2;
    private Company dummyCompany3;
    private Store store1;
    private Store store2;
    private Store store3;

    @BeforeEach
    void setUp() {
        dummyStores = new ArrayList<>();
        
        dummyCompany1 = new Company("name1", "email1");
        dummyCompany2 = new Company("name2", "email2");
        dummyCompany3 = new Company("name3", "email3");
        
        store1 = new Store("name1", "email1", dummyCompany1); 
        store2 = new Store("name2", "email2", dummyCompany2);
        store3 = new Store("name3", "email3", dummyCompany3);

        dummyStores.add(store1);
        dummyStores.add(store2);
        dummyStores.add(store3);
    }

    @Test
    void whenGetAllStores_thenReturnStoresList() throws Exception{
        
        when(storeService.getAllStores()).thenReturn(dummyStores);

        mvc.perform(get("/api/v1/stores/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(3))))
                .andExpect(jsonPath("$[0].name", is(dummyCompany1.getName())))
                .andExpect(jsonPath("$[1].name", is(dummyCompany2.getName())))
                .andExpect(jsonPath("$[2].name", is(dummyCompany3.getName())))
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

    // @Test
    // void whenCreateStore_thenReturnStore() throws Exception{
    //     List<Store> testStores = new ArrayList<>(Arrays.asList(store1));

    //     mvc.perform(post("/api/v1/stores/new").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(store1)));

    //     List<Store> returnedStores = storeService.getAllStores();

    //     assertEquals(testStores, returnedStores);

    // }

}
