package tie.backend.dto;

import tie.backend.model.Delivery;

public class PackageDTO {
    private String userName;
    private String userEmail;
    private Long deliveryId;

    public PackageDTO(){}
    public PackageDTO(String userName, String userEmail, Long deliveryId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.deliveryId = deliveryId;
    }

    public PackageDTO fromDelivery(Delivery delivery){
        this.userName = delivery.getUserName();
        this.userEmail = delivery.getUserEmail();
        this.deliveryId = delivery.getId();
        return this;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserName() {
        return userName;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
