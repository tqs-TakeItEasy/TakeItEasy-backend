package tie.backend.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "delivery")
public class Delivery {

    // TABLE

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userN")
    private String userName;

    @Column(name = "userE")
    private String userEmail;

    @Column(name = "packageI")
    private Long packageId;

    @ManyToOne()
    @JoinColumn(name = "pickup_point_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PickupPoint pickupPoint;

    @ManyToOne()
    @JoinColumn(name = "store_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Store store;

    @Column(name = "status")
    private String status;

    @Column(name = "registeryD")
    private String registeryDate;

    @Column(name = "deliveryD")
    private String deliveryDate;

    @Column(name = "pickupD")
    private String pickupDate;

    // CONSTRUCTORS

    public Delivery(){}
    public Delivery(String userName, String userEmail, Long packageId, PickupPoint pickupPoint, Store store) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.packageId = packageId;
        this.pickupPoint = pickupPoint;
        this.store = store;
        this.status = "DISPATCHED";
        this.registeryDate = LocalDate.now().toString();
        this.deliveryDate = null;
        this.pickupDate = null;
    }

    // GETTERS

    public String getDeliveryDate() {
        return deliveryDate;
    }
    public Long getId() {
        return id;
    }
    public Long getPackageId() {
        return packageId;
    }
    public String getPickupDate() {
        return pickupDate;
    }
    public PickupPoint getPickupPoint() {
        return pickupPoint;
    }
    public String getRegisteryDate() {
        return registeryDate;
    }
    public String getStatus() {
        return status;
    }
    public Store getStore() {
        return store;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserName() {
        return userName;
    }

    // SETTERS

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }
    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
    public void setPickupPoint(PickupPoint pickupPoint) {
        this.pickupPoint = pickupPoint;
    }
    public void setRegisteryDate(String registeryDate) {
        this.registeryDate = registeryDate;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setStore(Store store) {
        this.store = store;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Delivery)) {
            return false;
        }
        Delivery delivery = (Delivery) o;
        return  Objects.equals(userEmail, delivery.userEmail) && 
                Objects.equals(userName, delivery.userName) && 
                Objects.equals(packageId, delivery.packageId) && 
                Objects.equals(pickupPoint, delivery.pickupPoint) &&
                Objects.equals(store, delivery.store) && 
                Objects.equals(status, delivery.status) && 
                Objects.equals(registeryDate, delivery.registeryDate) && 
                Objects.equals(deliveryDate, delivery.deliveryDate) && 
                Objects.equals(pickupDate, delivery.pickupDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, userName, packageId, pickupPoint, store, status, registeryDate, deliveryDate, pickupDate);
    }

    // STRING REPRESENTATION

    @Override
    public String toString() {
        return  "{" +
                "id='" + getId() + "', " +
                "userEmail='" + getUserEmail() + "', " +
                "userName='" + getUserName() + "', " +
                "packageId='" + getPackageId() + "', " +
                "pickupPoint='" + getPickupPoint() + "', " +
                "store='" + getStore() + "', " +
                "status='" + getStatus() + "', " +
                "registeryDate='" + getRegisteryDate() + "', " +
                "deliveryDate='" + getDeliveryDate() + "'" +
                "pickupDate='" + getPickupDate() + "'" +
                "}";
    }
}
