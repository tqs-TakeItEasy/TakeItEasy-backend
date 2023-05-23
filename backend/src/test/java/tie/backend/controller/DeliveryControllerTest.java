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
import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.model.Store;
import tie.backend.service.DeliveryService;
import tie.backend.service.PickupPointService;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class DeliveryControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeliveryService deliveryService;

    @MockBean
    private PickupPointService pickupPointService;

    private List<Delivery> dummyDeliveries;
    private Company dummyCompany1;
    private Company dummyCompany2;
    private Company dummyCompany3;
    private Delivery dummyDelivery1;
    private Delivery dummyDelivery2;
    private Delivery dummyDelivery3;
    private PickupPoint dummyPickupPoint1;
    private PickupPoint dummyPickupPoint2;
    private PickupPoint dummyPickupPoint3;
    private Store dummyStore;

    @BeforeEach
    void setUp() {
        dummyDeliveries = new ArrayList<>();
        
        dummyPickupPoint1 = new PickupPoint("name1", "address1", "email1", dummyCompany1);
        dummyPickupPoint2 = new PickupPoint("name2", "address2", "email2", dummyCompany2);
        dummyPickupPoint3 = new PickupPoint("name3", "address3", "email3", dummyCompany3);
        
        dummyStore = new Store();

        dummyDelivery1 = new Delivery("userName1", "userEmail1", 10L, dummyPickupPoint1, dummyStore); 
        dummyDelivery2 = new Delivery("userName2", "userEmail2", 20L, dummyPickupPoint2, dummyStore);
        dummyDelivery3 = new Delivery("userName3", "userEmail1", 30L, dummyPickupPoint3, dummyStore);

        dummyDeliveries.add(dummyDelivery1);
        dummyDeliveries.add(dummyDelivery2);
        dummyDeliveries.add(dummyDelivery3);
    }

    @Test
    void whenGetAllDeliveries_thenReturnDeliveriesList() throws Exception{
        
        when(deliveryService.getAllDeliveries()).thenReturn(dummyDeliveries);

        mvc.perform(get("/api/v1/dummyDeliveries/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(3))))
                .andExpect(jsonPath("$[0].userName", is(dummyDelivery1.getUserName())))
                .andExpect(jsonPath("$[1].userName", is(dummyDelivery2.getUserName())))
                .andExpect(jsonPath("$[2].userName", is(dummyDelivery3.getUserName())))
        ;

        verify(deliveryService, times(1)).getAllDeliveries();
    }

    @Test
    void whenGetDeliveryById_thenReturnDelivery() throws Exception{

        Long id = 1L;
        dummyDelivery1.setId(id);
        
        when(deliveryService.getDeliveryById(dummyDelivery1.getId())).thenReturn(Optional.of(dummyDelivery1));

        mvc.perform(get("/api/v1/dummyDeliveries/delivery/" + Long.toString(id) + "/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(dummyDelivery1.getId().intValue())))
                .andExpect(jsonPath("$.userName", is(dummyDelivery1.getUserName())))
                .andExpect(jsonPath("$.userEmail", is(dummyDelivery1.getUserEmail())))
        ;

        verify(deliveryService, times(1)).getDeliveryById(dummyDelivery1.getId());
    }

    @Test
    void whenGetDeliveriesByPickupPointId_thenReturnDelivery() throws Exception{

        Long id = 1L;
        dummyDelivery1.getPickupPoint().setId(id);
        List<Delivery> dummyDeliveries = new ArrayList<>();
        dummyDeliveries.add(dummyDelivery1);
        
        when(pickupPointService.getPickupPointById(dummyDelivery1.getPickupPoint().getId())).thenReturn(Optional.of(dummyDelivery1.getPickupPoint()));
        when(deliveryService.getDeliveriesByPickupPoint(dummyDelivery1.getPickupPoint())).thenReturn(dummyDeliveries);

        mvc.perform(get("/api/v1/dummyDeliveries/point/" + Long.toString(id) + "/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userName", is(dummyDelivery1.getUserName())))
                .andExpect(jsonPath("$[0].userEmail", is(dummyDelivery1.getUserEmail())))
                .andExpect(jsonPath("$[0].packageId", is(dummyDelivery1.getPackageId().intValue())))
        ;

        verify(pickupPointService, times(1)).getPickupPointById(dummyDelivery1.getPickupPoint().getId());
        verify(deliveryService, times(1)).getDeliveriesByPickupPoint(dummyDelivery1.getPickupPoint());
    }
   
}
