package tie.backend.model;

import java.util.ArrayList;

public class Company {
    private String Name;
    private String Email;
    private ArrayList<PickupPoint> pickupPoints;

    public Company(){}

    public String getEmail() {
        return Email;
    }
    public String getName() {
        return Name;
    }
    public ArrayList<PickupPoint> getPickupPoints() {
        return pickupPoints;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setPickupPoints(ArrayList<PickupPoint> pickupPoints) {
        this.pickupPoints = pickupPoints;
    }
}
