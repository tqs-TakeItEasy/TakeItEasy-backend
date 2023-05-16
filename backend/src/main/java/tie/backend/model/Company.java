package tie.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    // @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private List<PickupPoint> pickupPoints;

    // @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private List<Notification> notifications;

    public Company(){}

    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    // public List<PickupPoint> getPickupPoints() {
    //     return pickupPoints;
    // }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    // public void setPickupPoints(List<PickupPoint> pickupPoints) {
    //     this.pickupPoints = pickupPoints;
    // }
}
