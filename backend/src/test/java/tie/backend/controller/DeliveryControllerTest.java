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

    private List<Delivery> deliveries;
    private Delivery delivery1;
    private Delivery delivery2;
    private Delivery delivery3;
    private PickupPoint pickupPoint1;
    private PickupPoint pickupPoint2;
    private PickupPoint pickupPoint3;
    private Store store;

    @BeforeEach
    void setUp() {
        deliveries = new ArrayList<>();
        
        pickupPoint1 = new PickupPoint("name1", "address1", "email1");
        pickupPoint2 = new PickupPoint("name2", "address2", "email2");
        pickupPoint3 = new PickupPoint("name3", "address3", "email3");
        
        store = new Store();

        delivery1 = new Delivery("userName1", "userEmail1", 10L, pickupPoint1, store); 
        delivery2 = new Delivery("userName2", "userEmail2", 20L, pickupPoint2, store);
        delivery3 = new Delivery("userName3", "userEmail1", 30L, pickupPoint3, store);

        deliveries.add(delivery1);
        deliveries.add(delivery2);
        deliveries.add(delivery3);
    }

    @Test
    void whenGetAllDeliveries_thenReturnDeliveriesList() throws Exception{
        
        when(deliveryService.getAllDeliveries()).thenReturn(deliveries);

        mvc.perform(get("/api/v1/deliveries/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(3))))
                .andExpect(jsonPath("$[0].userName", is(delivery1.getUserName())))
                .andExpect(jsonPath("$[1].userName", is(delivery2.getUserName())))
                .andExpect(jsonPath("$[2].userName", is(delivery3.getUserName())))
        ;

        verify(deliveryService, times(1)).getAllDeliveries();
    }

    @Test
    void whenGetDeliveryById_thenReturnDelivery() throws Exception{

        Long id = 1L;
        delivery1.setId(id);
        
        when(deliveryService.getDeliveryById(delivery1.getId())).thenReturn(Optional.of(delivery1));

        mvc.perform(get("/api/v1/deliveries/delivery/" + Long.toString(id) + "/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(delivery1.getId().intValue())))
                .andExpect(jsonPath("$.userName", is(delivery1.getUserName())))
                .andExpect(jsonPath("$.userEmail", is(delivery1.getUserEmail())))
        ;

        verify(deliveryService, times(1)).getDeliveryById(delivery1.getId());
    }

    @Test
    void whenGetDeliveriesByPickupPointId_thenReturnDelivery() throws Exception{

        Long id = 1L;
        delivery1.getPickupPoint().setId(id);
        List<Delivery> dummyDeliveries = new ArrayList<>();
        dummyDeliveries.add(delivery1);
        
        when(pickupPointService.getPickupPointById(delivery1.getPickupPoint().getId())).thenReturn(Optional.of(delivery1.getPickupPoint()));
        when(deliveryService.getDeliveriesByPickupPoint(delivery1.getPickupPoint())).thenReturn(dummyDeliveries);

        mvc.perform(get("/api/v1/deliveries/point/" + Long.toString(id) + "/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userName", is(delivery1.getUserName())))
                .andExpect(jsonPath("$[0].userEmail", is(delivery1.getUserEmail())))
                .andExpect(jsonPath("$[0].packageId", is(delivery1.getPackageId().intValue())))
        ;

        verify(pickupPointService, times(1)).getPickupPointById(delivery1.getPickupPoint().getId());
        verify(deliveryService, times(1)).getDeliveriesByPickupPoint(delivery1.getPickupPoint());
    }
   
}
