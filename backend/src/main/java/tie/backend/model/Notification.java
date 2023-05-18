package tie.backend.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {

    // TABLE

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_email")
    private String companyEmail;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "message")
    private String message;

    // CONSTRUCTORS

    public Notification(){}
    public Notification(String userEmail, String message){
        this.companyEmail = "takeiteasydistributions@gmail.com";
        this.userEmail = userEmail;
        this.message = message;
    }
    public Notification(String companyEmail, String userEmail, String message){
        this.companyEmail = companyEmail;
        this.userEmail = userEmail;
        this.message = message;
    }

    // GETTERS

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

    // SETTERS

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

    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Notification)) {
            return false;
        }
        Notification notification = (Notification) o;
        return  Objects.equals(companyEmail, notification.companyEmail) && 
                Objects.equals(userEmail, notification.userEmail) && 
                Objects.equals(message, notification.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyEmail, userEmail, message);
    }

    // STRING REPRESENTATION

    @Override
    public String toString() {
        return  "{" +
                "id='" + getId() + "', " +
                "companyEmail='" + getCompanyEmail() + "', " +
                "userEmail='" + getUserEmail() + "', " +
                "message='" + getMessage() + "', " +
                "}";
    }
}
