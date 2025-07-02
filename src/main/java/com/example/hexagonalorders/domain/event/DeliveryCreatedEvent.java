package com.example.hexagonalorders.domain.event;

import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;
import com.example.hexagonalorders.domain.model.valueobject.RouteId;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryPersonId;

public class DeliveryCreatedEvent extends DomainEvent {
    private final DeliveryId deliveryId;
    private final RouteId routeId;
    private final DeliveryPersonId deliveryPersonId;

    public DeliveryCreatedEvent(DeliveryId deliveryId, RouteId routeId, DeliveryPersonId deliveryPersonId) {
        this.deliveryId = deliveryId;
        this.routeId = routeId;
        this.deliveryPersonId = deliveryPersonId;
    }

    public DeliveryId getDeliveryId() {
        return deliveryId;
    }

    public RouteId getRouteId() {
        return routeId;
    }

    public DeliveryPersonId getDeliveryPersonId() {
        return deliveryPersonId;
    }
} 