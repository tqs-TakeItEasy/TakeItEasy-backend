package tie.backend.model;

import java.util.ArrayList;

public class PickupPoint {
    private Long id;
    private String name;
    private Address address;
    private PickupPointStatus status;
    private ArrayList<Delivery> allDeliveries;
    private ArrayList<Delivery> onGoingDeliveries;

    public PickupPoint(){}

    public Address getAddress() {
        return address;
    }
    public ArrayList<Delivery> getAllDeliveries() {
        return allDeliveries;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Delivery> getOnGoingDeliveries() {
        return onGoingDeliveries;
    }
    public PickupPointStatus getStatus() {
        return status;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setAllDeliveries(ArrayList<Delivery> allDeliveries) {
        this.allDeliveries = allDeliveries;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setOnGoingDeliveries(ArrayList<Delivery> onGoingDeliveries) {
        this.onGoingDeliveries = onGoingDeliveries;
    }
    public void setStatus(PickupPointStatus status) {
        this.status = status;
    }
}
