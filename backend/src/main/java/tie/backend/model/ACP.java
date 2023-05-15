package tie.backend.model;
import java.util.ArrayList;

public class ACP {
    private Long id;
    private String name;
    private String email;
    private String pwd;
    private ArrayList<String> roles;

    public ACP(){}

    public String getEmail() {
        return email;
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
    public ArrayList<String> getRoles() {
        return roles;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
}
