package tie.backend.model;

public class Notification {
    private Long id;
    private String companyEmail;
    private String userEmail;
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
