package tie.backend.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import tie.backend.service.CompanyService;
import tie.backend.service.StoreService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class StoreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StoreService storeService;

    @MockBean
    private CompanyService companyService;

    private List<Store> dummyStores;
    private Company dummyCompany1;
    private Company dummyCompany2;
    private Company dummyCompany3;
    private Store dummyStore1;
    private Store dummyStore2;
    private Store dummyStore3;

    @BeforeEach
    void setUp() {
        dummyStores = new ArrayList<>();
        
        dummyCompany1 = new Company("name1", "email1");
        dummyCompany2 = new Company("name2", "email2");
        dummyCompany3 = new Company("name3", "email3");
        
        dummyStore1 = new Store("name1", "email1", dummyCompany1); 
        dummyStore2 = new Store("name2", "email2", dummyCompany2);
        dummyStore3 = new Store("name3", "email3", dummyCompany3);

        dummyStores.add(dummyStore1);
        dummyStores.add(dummyStore2);
        dummyStores.add(dummyStore3);
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

        verify(storeService, times(1)).getAllStores();
    }

    @Test
    void whenGetStoreById_thenReturnStore() throws Exception{

        Long id = 1L;
        dummyStore1.setId(id);
        
        when(storeService.getStoreById(dummyStore1.getId())).thenReturn(Optional.of(dummyStore1));

        mvc.perform(get("/api/v1/stores/store/" + Long.toString(id) + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(dummyStore1.getId().intValue())))
            .andExpect(jsonPath("$.name", is(dummyStore1.getName())))
            .andExpect(jsonPath("$.email", is(dummyStore1.getEmail())))
        ;
        
        verify(storeService, times(1)).getStoreById(dummyStore1.getId());
    }

    @Test
    void whenAddStore_thenReturnStore() throws Exception {

        when(storeService.addStore(dummyStore1)).thenReturn(dummyStore1);

        // mvc.perform(post("/api/v1/stores/add/")
        //         .contentType(MediaType.APPLICATION_JSON)
        //         .content(JsonUtils.toJson(dummyStore1)))
        //         .andDo(print())
        //         .andExpect(status().isOk())
        //         .andExpect(jsonPath("$.name").value(dummyStore1.getName()))
        //         .andExpect(jsonPath("$.email").value(dummyStore1.getEmail()));

        // verify(storeService, times(1)).addStore(dummyStore1);
    }
}