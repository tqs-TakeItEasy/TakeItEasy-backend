package tie.backend.dto;

import tie.backend.model.Delivery;

public class PackageDTO {
    private Long deliveryId;

    public PackageDTO() {}
    public PackageDTO(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public PackageDTO fromDelivery(Delivery delivery){
        this.deliveryId = delivery.getId();
        return this;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }
}