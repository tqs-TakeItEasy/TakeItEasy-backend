package tie.backend.dto;

import tie.backend.model.Store;

public class StoreDTO {
    private String name;
    private String email;
    private Long companyId;

    public StoreDTO() {}
    public StoreDTO(String name, String email, Long companyId) {
        this.name = name;
        this.email = email;
        this.companyId = companyId;
    }

    public StoreDTO fromStore(Store store) {
        this.name = store.getName();
        this.email = store.getEmail();
        this.companyId = store.getCompany().getId();
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
}
