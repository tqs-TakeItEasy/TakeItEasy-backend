package tie.backend.controller;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import tie.backend.model.PickupPoint;
import tie.backend.service.CompanyService;
import tie.backend.service.PickupPointService;



@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class PickupPointControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PickupPointService pickupPointService;

    @MockBean
    private CompanyService companyService;

    private Company dummyCompany1;
    private PickupPoint dummyPickupPoint1;

    @BeforeEach
    void setUp() {
        dummyCompany1 = new Company("name1", "email1");

        dummyPickupPoint1 = new PickupPoint("name1", "address1", "email1", dummyCompany1);
        // dummyPickupPoint1.setId(1L);
    }

    @Test
    void whenAddPickupPoint_thenReturnPickupPoint() throws Exception {

        when(companyService.addCompany(dummyCompany1)).thenReturn(dummyCompany1);
        when(pickupPointService.addPickupPoint(dummyPickupPoint1)).thenReturn(dummyPickupPoint1);

        mvc.perform(post("/api/v1/pickuppoints/add/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(dummyPickupPoint1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(dummyPickupPoint1.getName()))
                .andExpect(jsonPath("$.address").value(dummyPickupPoint1.getAddress()))
                .andExpect(jsonPath("$.email").value(dummyPickupPoint1.getEmail()));

        verify(pickupPointService, times(1)).addPickupPoint(dummyPickupPoint1);
    }


}
