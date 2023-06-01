package tie.backend.dto;

import tie.backend.model.Delivery;

public class DeliveryDTO {
    private String userName;
    private String userEmail;
    private Long packageId;
    private Long pickupPointId;
    private Long storeId;

    public DeliveryDTO(){}
    public DeliveryDTO(String userName, String userEmail, Long packageId, Long pickupPointId, Long storeId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.packageId = packageId;
        this.pickupPointId = pickupPointId;
        this.storeId = storeId;
    }

    public DeliveryDTO fromDelivery(Delivery delivery){
        this.userName = delivery.getUserName();
        this.userEmail = delivery.getUserEmail();
        this.packageId = delivery.getPackageId();
        this.pickupPointId = delivery.getPickupPoint().getId();
        this.storeId = delivery.getStore().getId();
        return this;
    }

    public Long getPackageId() {
        return packageId;
    }
    public Long getPickupPointId() {
        return pickupPointId;
    }
    public Long getStoreId() {
        return storeId;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserName() {
        return userName;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
    public void setPickupPointId(Long pickupPointId) {
        this.pickupPointId = pickupPointId;
    }
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}