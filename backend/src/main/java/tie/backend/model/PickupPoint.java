package tie.backend.model;

import java.util.Objects;

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

    // TABLE

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private PickupPointStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    // CONSTRUCTORS

    public PickupPoint(){}
    public PickupPoint(String name, String address) {
        this.name = name;
        this.address = address;
        this.status = PickupPointStatus.AVAILABLE;
    }

    // GETTERS

    public String getAddress() {
        return address;
    }
    public Company getCompany() {
        return company;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public PickupPointStatus getStatus() {
        return status;
    }

    // SETTERS

    public void setAddress(String address) {
        this.address = address;
    }
    public void setCompany(Company company) {
        this.company = company;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStatus(PickupPointStatus status) {
        this.status = status;
    }

    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PickupPoint)) {
            return false;
        }
        PickupPoint pickupPoint = (PickupPoint) o;
        return  Objects.equals(name, pickupPoint.name) && 
                Objects.equals(address, pickupPoint.address) && 
                Objects.equals(status, pickupPoint.status) && 
                Objects.equals(company, pickupPoint.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, status, company);
    }

    // STRING REPRESENTATION

    @Override
    public String toString() {
        return  "{" +
                "id='" + getId() + "', " +
                "name='" + getName() + "', " +
                "address='" + getAddress() + "', " +
                "status='" + getStatus() + "', " +
                "company='" + getCompany() + "'" +
                "}";
    }
}
