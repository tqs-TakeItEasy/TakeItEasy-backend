package tie.backend.dto;

import tie.backend.model.PickupPoint;

public class PickupPointDTO {
    private String name;
    private String address;
    private String email;
    private Long companyId;

    public PickupPointDTO(){}
    public PickupPointDTO(String name, String address, String email, Long companyId) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.companyId = companyId;
    }

    public PickupPointDTO fromPickupPoint(PickupPoint pickupPoint){
        this.name = pickupPoint.getName();
        this.address = pickupPoint.getAddress();
        this.email = pickupPoint.getEmail();
        this.companyId = pickupPoint.getCompany().getId();
        return this;
    }

    public String getAddress() {
        return address;
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
    public void setAddress(String address) {
        this.address = address;
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