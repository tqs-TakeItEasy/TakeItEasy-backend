package tie.backend.controller;


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
import tie.backend.model.PickupPoint;
import tie.backend.service.PickupPointService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class PickupPointControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PickupPointService pickupPointService;

    private PickupPoint dummyPickupPoint1;

    @BeforeEach
    void setUp() {
        dummyPickupPoint1 = new PickupPoint("name1", "address1", "email1");
        dummyPickupPoint1.setId(1L);
    }

    @Test
    void whenAddNewValidPickupPoint_thenReturnPickupPointAnd200() throws Exception {
        // mock the service response
        when(pickupPointService.addPickupPoint(dummyPickupPoint1)).thenReturn(dummyPickupPoint1);

        // perform the request
        mvc.perform(post("/api/v1/pickuppoint/add/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(dummyPickupPoint1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(dummyPickupPoint1.getName()))
                .andExpect(jsonPath("$.address").value(dummyPickupPoint1.getAddress()))
                .andExpect(jsonPath("$.email").value(dummyPickupPoint1.getEmail()));

        // verify if the method is called
        verify(pickupPointService, times(1)).addPickupPoint(dummyPickupPoint1);
    }


}
