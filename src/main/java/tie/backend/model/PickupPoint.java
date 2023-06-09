package tie.backend.model;

import java.util.Objects;

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

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @ManyToOne()
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    // CONSTRUCTORS

    public PickupPoint(){}
    public PickupPoint(String name, String address, String email, Company company) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.status = "AVAILABLE";
        this.company = company;
    }

    // GETTERS

    public String getAddress() {
        return address;
    }
    public String getEmail() {
        return email;
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
    public String getStatus() {
        return status;
    }

    // SETTERS

    public void setAddress(String address) {
        this.address = address;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public void setStatus(String
     status) {
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
                Objects.equals(email, pickupPoint.email) &&
                Objects.equals(status, pickupPoint.status) && 
                Objects.equals(company, pickupPoint.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, email, status, company);
    }

    // STRING REPRESENTATION

    @Override
    public String toString() {
        return  "{" +
                "id='" + getId() + "', " +
                "name='" + getName() + "', " +
                "address='" + getAddress() + "', " +
                "email='" + getEmail() + "', " +
                "status='" + getStatus() + "', " +
                "company='" + getCompany() + "'" +
                "}";
    }
}
