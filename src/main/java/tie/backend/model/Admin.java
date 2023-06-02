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
@Table(name = "admin")
public class Admin {

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

    @ManyToOne()
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    // CONSTRUCTORS

    public Admin(){}
    public Admin(String name, String email, String pwd, Company company){
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.company = company;
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
    public Company getCompany() {
        return company;
    }
    public String getPwd() {
        return pwd;
    }

    // SETTERS

    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setCompany(Company company) {
        this.company = company;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    // EQUALS AND HASH

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Admin)) {
            return false;
        }
        Admin admin = (Admin) o;
        return  Objects.equals(name, admin.name) && 
                Objects.equals(email, admin.email) && 
                Objects.equals(pwd, admin.pwd) && 
                Objects.equals(company, admin.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, pwd, company);
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
                "}";
    }
}
