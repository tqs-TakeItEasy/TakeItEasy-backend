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
@Table(name = "acp")
public class ACP {

    // TABLE

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "pwd")
    private String pwd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_point_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PickupPoint pickupPoint;

    // CONSTRUCTORS

    public ACP(){}
    public ACP(String name, String email, String pwd, Company company, PickupPoint pickupPoint){
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.company = company;
        this.pickupPoint = pickupPoint;
    }

    // GETTERS

    public String getEmail() {
        return email;
    }
    public PickupPoint getPickupPoint() {
        return pickupPoint;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPwd() {
        return pwd;
    }
    public Company getCompany() {
        return company;
    }

    // SETTERS


    public void setEmail(String email) {
        this.email = email;
    }
    public void setPickupPoint(PickupPoint pickupPoint) {
        this.pickupPoint = pickupPoint;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PickupPoint)) {
            return false;
        }
        ACP aCP = (ACP) o;
        return  Objects.equals(name, aCP.name) && 
                Objects.equals(email, aCP.email) && 
                Objects.equals(pwd, aCP.pwd) && 
                Objects.equals(company, aCP.company) && 
                Objects.equals(pickupPoint, aCP.pickupPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, pwd, company, pickupPoint);
    }

    // STRING REPRESENTATION

    @Override
    public String toString() {
        return  "{" +
                "id='" + getId() + "', " +
                "name='" + getName() + "', " +
                "email='" + getEmail() + "', " +
                "pwd='" + getPwd() + "', " +
                "company='" + getCompany() + "'" +
                "pickupPoint='" + getPickupPoint() + "'" +
                "}";
    }
}
