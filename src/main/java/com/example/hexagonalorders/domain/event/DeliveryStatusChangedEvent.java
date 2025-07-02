package com.example.hexagonalorders.domain.event;

import com.example.hexagonalorders.domain.model.DeliveryStatus;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;

public class DeliveryStatusChangedEvent extends DomainEvent {
    private final DeliveryId deliveryId;
    private final DeliveryStatus oldStatus;
    private final DeliveryStatus newStatus;

    public DeliveryStatusChangedEvent(DeliveryId deliveryId, DeliveryStatus oldStatus, DeliveryStatus newStatus) {
        this.deliveryId = deliveryId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public DeliveryId getDeliveryId() {
        return deliveryId;
    }

    public DeliveryStatus getOldStatus() {
        return oldStatus;
    }

    public DeliveryStatus getNewStatus() {
        return newStatus;
    }
} 