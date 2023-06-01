package tie.backend.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import tie.backend.config.JsonUtils;
import tie.backend.model.Company;
import tie.backend.model.PickupPoint;
import tie.backend.service.PickupPointService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class PickupPointControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PickupPointService pickupPointService;

    private List<PickupPoint> dummyPickupPoints;
    private Company dummyCompany1;
    private Company dummyCompany2;
    private Company dummyCompany3;
    private PickupPoint dummyPickupPoint1;
    private PickupPoint dummyPickupPoint2;
    private PickupPoint dummyPickupPoint3;

    @BeforeEach
    void setUp() {
        dummyPickupPoints = new ArrayList<>();
        
        dummyCompany1 = new Company("name1", "email1");
        dummyCompany2 = new Company("name2", "email2");
        dummyCompany3 = new Company("name3", "email3");

        dummyPickupPoint1 = new PickupPoint("name1", "address1", "email1", dummyCompany1);
        dummyPickupPoint2 = new PickupPoint("name2", "address2", "email2", dummyCompany2);
        dummyPickupPoint3 = new PickupPoint("name3", "address3", "email3", dummyCompany3);

        dummyPickupPoints.add(dummyPickupPoint1);
        dummyPickupPoints.add(dummyPickupPoint2);
        dummyPickupPoints.add(dummyPickupPoint3);
    }

    @Test
    void whenGetAllPickupPoints_thenReturnPickupPointsList() throws Exception{
        
        when(pickupPointService.getAllPickupPoints()).thenReturn(dummyPickupPoints);

        mvc.perform(get("/api/v1/pickuppoints/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(equalTo(3))))
            .andExpect(jsonPath("$[0].name", is(dummyPickupPoint1.getName())))
            .andExpect(jsonPath("$[1].name", is(dummyPickupPoint2.getName())))
            .andExpect(jsonPath("$[2].name", is(dummyPickupPoint3.getName())))
        ;

        verify(pickupPointService, times(1)).getAllPickupPoints();
    }

    @Test
    void whenGetPickupPointByStatus_thenReturnDelivery() throws Exception{

        when(pickupPointService.getPickupPointsByStatus("AVAILABLE")).thenReturn(dummyPickupPoints);

        mvc.perform(get("/api/v1/pickuppoints/status/" + "AVAILABLE" + "/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name").value(dummyPickupPoint1.getName()))
            .andExpect(jsonPath("$[1].address").value(dummyPickupPoint2.getAddress()))
            .andExpect(jsonPath("$[2].email").value(dummyPickupPoint3.getEmail()));
        ;

        verify(pickupPointService, times(1)).getPickupPointsByStatus("AVAILABLE");
    }

    @Test
    void whenAddPickupPoint_thenReturnPickupPoint() throws Exception {

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

    @Test
    void whenDeletePickupPoint_thenReturnPickupPoint() throws Exception {

        Long id = 1L;
        dummyPickupPoint1.setId(id);

        when(pickupPointService.deletePickupPointById(dummyPickupPoint1.getId())).thenReturn(dummyPickupPoint1);

        mvc.perform(delete("/api/v1/pickuppoints/" + Long.toString(id) + "/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(dummyPickupPoint1.getName()))
            .andExpect(jsonPath("$.address").value(dummyPickupPoint1.getAddress()))
            .andExpect(jsonPath("$.email").value(dummyPickupPoint1.getEmail()));

        verify(pickupPointService, times(1)).deletePickupPointById(dummyPickupPoint1.getId());
    }
}