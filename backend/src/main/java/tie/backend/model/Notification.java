package tie.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_email")
    private String companyEmail;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "message")
    private String message;

    public Notification(){}

    public String getCompanyEmail() {
        return companyEmail;
    }
    public Long getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
