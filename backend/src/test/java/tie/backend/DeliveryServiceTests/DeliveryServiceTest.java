package tie.backend.DeliveryServiceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tie.backend.model.*;
import tie.backend.repository.DeliveryRepository;
import tie.backend.repository.PickupPointRepository;
import tie.backend.service.DeliveryService;
import tie.backend.service.PickupPointService;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

class DeliveryServiceTest {


    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    private ArrayList<Delivery> dummyDeliveries;
    private Delivery dummyDelivery1;
    private Delivery dummyDelivery2;
    private PickupPoint dummyPickupPoint;

    @BeforeEach
    void setUp() {
        dummyDeliveries = new ArrayList<Delivery>();
        dummyPickupPoint = new PickupPoint("PickUpPoint", "Rua das Amoreiras 5150-234 Lisboa", PickupPointStatus.AVAILABLE);
        dummyDelivery1 = new Delivery("Loja", "User", "User@gmail.com", 22L, dummyPickupPoint);
        dummyDelivery2 = new Delivery("Loja2", "User2", "User2@gmail.com", 24L, dummyPickupPoint);
        dummyDeliveries.add(dummyDelivery1);
        dummyDeliveries.add(dummyDelivery2);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDeliveries(){
        when(deliveryRepository.findAll()).thenReturn(dummyDeliveries);

        Object actualDeliveryService = deliveryService.getAllDeliveries(); // should be ArrayList<Delivery>

        assertThat(actualDeliveryService).isEqualTo(dummyDeliveries);

    }

    @Test
    void getAllDeliveriesFromPickupPoint(){
        Long ID = 22L;

        when(deliveryRepository.findByPickupPointId(anyLong())).thenReturn(dummyDeliveries);

        Object actualPickupPoint = deliveryService.getDeliveriesByPickupPointId(ID);

        assertThat(actualPickupPoint).isEqualTo(dummyDeliveries);


    }

    @Test
    void getDeliveryByid(){
        Long ID = 24L;

        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(dummyDelivery2));

        Object actualDeliveryService = deliveryService.getDeliveryById(ID);;

        assertThat(actualDeliveryService).isEqualTo(dummyDelivery2);
    }

}
