package tie.backend.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "company")
// @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Company{

    // TABLE
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    // CONSTRUCTORS

    public Company(){}
    public Company(String name, String email){
        this.name = name;
        this.email = email;
    }

    // GETTERS

    public String getEmail() {
        return email;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    // SETTERS

    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Company)) {
            return false;
        }
        Company company = (Company) o;
        return  Objects.equals(name, company.name) && 
                Objects.equals(email, company.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }

    // STRING REPRESENTATION

    @Override
    public String toString() {
        return  "{" +
                "id='" + getId() + "', " +
                "name='" + getName() + "', " +
                "email='" + getEmail() + "', " +
                "}";
    }
}
