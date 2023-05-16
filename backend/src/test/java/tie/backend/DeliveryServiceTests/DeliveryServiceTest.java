package tie.backend.DeliveryServiceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import tie.backend.model.*;
import tie.backend.service.DeliveryService;

import java.util.ArrayList;

public class DeliveryServiceTest {

    @InjectMocks
    private DeliveryService deliveryService;

    private ArrayList<Delivery> dummyDeliveries;
    private Delivery dummyDelivery1;
    private Delivery dummyDelivery2;
    private PickupPoint dummyPickupPoint;

    @BeforeEach
    void setUp() {
        dummyDeliveries = new ArrayList<Delivery>();
        dummyPickupPoint = new PickupPoint("PickUpPoint", new Address("Rua das Amoreiras", "5150-234", "Lisboa"), PickupPointStatus.AVAILABLE);
        dummyDelivery1 = new Delivery("Loja", "User", "User@gmail.com", 55L, new PickupPoint(), DeliveryStatus.DISPATCHED, "02/03/2023");
        dummyDelivery2 = new Delivery("Loja2", "User2", "User2@gmail.com", 56L, new PickupPoint(), DeliveryStatus.RECIEVED, "02/05/2023");
        dummyDeliveries.add(dummyDelivery1);
        dummyDeliveries.add(dummyDelivery2);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDeliveries(){
        deliveryService.getAllDeliveries();
    }

    @Test
    void getAllDeliveriesFromPickupStore(){
        Long ID = 22L;
        deliveryService.getPickUpPointDelivires(ID);
    }

    @Test
    void getDeliveryByid(){
        Long ID = 55L;
        deliveryService.getDeliveryById(ID);
    }

}
