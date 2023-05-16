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
@Table(name = "pickup_point")
public class PickupPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private PickupPointStatus status;

    // @ManyToOne
    // @JoinColumn(name = "company_id")
    // private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    // @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private List<Delivery> allDeliveries;

    // @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private List<Delivery> onGoingDeliveries;

    public PickupPoint(){}

    public String getAddress() {
        return address;
    }
    // public List<Delivery> getAllDeliveries() {
    //     return allDeliveries;
    // }
    public Company getCompany() {
        return company;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    // public List<Delivery> getOnGoingDeliveries() {
    //     return onGoingDeliveries;
    // }
    public PickupPointStatus getStatus() {
        return status;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCompany(Company company) {
        this.company = company;
    }
    // public void setAllDeliveries(List<Delivery> allDeliveries) {
    //     this.allDeliveries = allDeliveries;
    // }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    // public void setOnGoingDeliveries(List<Delivery> onGoingDeliveries) {
    //     this.onGoingDeliveries = onGoingDeliveries;
    // }
    public void setStatus(PickupPointStatus status) {
        this.status = status;
    }
}
