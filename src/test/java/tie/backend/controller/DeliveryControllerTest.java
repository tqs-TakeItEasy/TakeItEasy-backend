package tie.backend.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import tie.backend.config.JsonUtils;
import tie.backend.dto.DeliveryDTO;
import tie.backend.model.Company;
import tie.backend.model.Delivery;
import tie.backend.model.PickupPoint;
import tie.backend.model.Store;
import tie.backend.service.DeliveryService;
import tie.backend.service.PickupPointService;
import tie.backend.service.StoreService;


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

    @MockBean
    private StoreService storeService;

    private List<Delivery> dummyDeliveries;
    private Company dummyCompany1;
    private Company dummyCompany2;
    private Company dummyCompany3;
    private Delivery dummyDelivery1;
    private Delivery dummyDelivery1_5;
    private Delivery dummyDelivery2;
    private Delivery dummyDelivery3;
    private PickupPoint dummyPickupPoint1;
    private PickupPoint dummyPickupPoint2;
    private PickupPoint dummyPickupPoint3;
    private Store dummyStore;

    @BeforeEach
    void setUp() {
        dummyDeliveries = new ArrayList<>();
        
        dummyCompany1 = new Company("name1", "email1");
        dummyCompany2 = new Company("name2", "email2");
        dummyCompany3 = new Company("name3", "email3");
        
        dummyPickupPoint1 = new PickupPoint("name1", "address1", "email1", dummyCompany1);
        dummyPickupPoint2 = new PickupPoint("name2", "address2", "email2", dummyCompany2);
        dummyPickupPoint3 = new PickupPoint("name3", "address3", "email3", dummyCompany3);
        
        dummyStore = new Store();

        dummyDelivery1 = new Delivery("userName1", "userEmail1", 10L, dummyPickupPoint1, dummyStore);
        dummyDelivery1_5 = new Delivery("userName1", "userEmail1", 10L, dummyPickupPoint1, dummyStore);
        dummyDelivery2 = new Delivery("userName2", "userEmail2", 20L, dummyPickupPoint2, dummyStore);
        dummyDelivery3 = new Delivery("userName3", "userEmail1", 30L, dummyPickupPoint3, dummyStore);

        dummyDeliveries.add(dummyDelivery1);
        dummyDeliveries.add(dummyDelivery2);
        dummyDeliveries.add(dummyDelivery3);

        dummyDelivery1_5.setStatus("RECIEVED");
    }

    @Test
    void whenGetAllDeliveries_thenReturnDeliveriesList() throws Exception{
        
        when(deliveryService.getAllDeliveries()).thenReturn(dummyDeliveries);

        mvc.perform(get("/api/v1/deliveries/")
            .contentType(MediaType.APPLICATION_JSON))
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

        mvc.perform(get("/api/v1/deliveries/delivery/" + Long.toString(id) + "/")
            .contentType(MediaType.APPLICATION_JSON))
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

        mvc.perform(get("/api/v1/deliveries/point/" + Long.toString(id) + "/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].userName", is(dummyDelivery1.getUserName())))
            .andExpect(jsonPath("$[0].userEmail", is(dummyDelivery1.getUserEmail())))
            .andExpect(jsonPath("$[0].packageId", is(dummyDelivery1.getPackageId().intValue())))
        ;

        verify(pickupPointService, times(1)).getPickupPointById(dummyDelivery1.getPickupPoint().getId());
        verify(deliveryService, times(1)).getDeliveriesByPickupPoint(dummyDelivery1.getPickupPoint());
    }

    @Test
    void whenAddDelivery_thenReturnDelivery() throws Exception {

        dummyDelivery1.setId(1L);
        dummyDelivery1.getPickupPoint().setId(2L);
        dummyDelivery1.getStore().setId(3L);

        DeliveryDTO deliveryDTO = new DeliveryDTO();
        deliveryDTO.fromDelivery(dummyDelivery1);

        when(pickupPointService.getPickupPointById(dummyDelivery1.getPickupPoint().getId())).thenReturn(Optional.of(dummyDelivery1.getPickupPoint()));
        when(storeService.getStoreById(dummyDelivery1.getStore().getId())).thenReturn(Optional.of(dummyDelivery1.getStore()));
        when(deliveryService.addDelivery(dummyDelivery1)).thenReturn(dummyDelivery1);

        mvc.perform(post("/api/v1/deliveries/add/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(deliveryDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.deliveryId").value(dummyDelivery1.getId().intValue()));

        verify(deliveryService, times(1)).addDelivery(dummyDelivery1);
    }
    @Test
    void whenUpdateDeliveryStatus_thenReturnDelivery() throws Exception {
        when(deliveryService.updateDelivery(dummyDelivery1_5)).thenReturn(dummyDelivery1_5);

        mvc.perform(put("/api/v1/deliveries/update/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(dummyDelivery1_5)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.packageId").value(dummyDelivery1_5.getPackageId().intValue()))
            .andExpect(jsonPath("$.status").value(dummyDelivery1_5.getStatus().toString()));

        verify(deliveryService, times(1)).updateDelivery(dummyDelivery1_5);
    }
}
