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
@Table(name = "acp")
public class ACP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "pwd")
    private String pwd;

    // @ManyToOne
    // @JoinColumn(name = "company_id")
    // private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    // private List<String> roles;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_point_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PickupPoint pickupPoint;

    public ACP(){}

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
    // public List<String> getRoles() {
    //     return roles;
    // }
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
    // public void setRoles(List<String> roles) {
    //     this.roles = roles;
    // }
}