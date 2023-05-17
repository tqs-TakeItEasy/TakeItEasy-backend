package tie.backend.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "package_id")
    private Long packageId;

    // @ManyToOne
    // @JoinColumn(name = "company_id")
    // private PickupPoint pickupPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_point_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PickupPoint pickupPoint;

    @Column(name = "status")
    private DeliveryStatus status;

    @Column(name = "registery_date")
    private String registeryDate;

    @Column(name = "delivery_date")
    private String deliveryDate;

    @Column(name = "pickup_date")
    private String pickupDate;

    private Delivery(){}

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
    public DeliveryStatus getStatus() {
        return status;
    }
    public String getStoreName() {
        return storeName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserName() {
        return userName;
    }
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
    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}