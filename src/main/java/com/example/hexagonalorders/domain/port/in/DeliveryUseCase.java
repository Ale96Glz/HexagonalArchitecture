package com.example.hexagonalorders.domain.port.in;

import com.example.hexagonalorders.domain.model.Delivery;
import com.example.hexagonalorders.domain.model.DeliveryStatus;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;
import com.example.hexagonalorders.domain.model.valueobject.RouteId;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryPersonId;
import com.example.hexagonalorders.domain.model.valueobject.Address;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DeliveryUseCase {
    
    
    Delivery createDelivery(DeliveryId deliveryId, RouteId routeId, DeliveryPersonId deliveryPersonId,
                           Address deliveryAddress, OrderNumber orderNumber, LocalDateTime scheduledDate);
    
    Optional<Delivery> getDeliveryById(DeliveryId deliveryId);
    
    List<Delivery> getDeliveriesByRoute(RouteId routeId);
    
    List<Delivery> getDeliveriesByDeliveryPerson(DeliveryPersonId deliveryPersonId);
    
    List<Delivery> getDeliveriesByOrder(OrderNumber orderNumber);
    List<Delivery> getDeliveriesByStatus(DeliveryStatus status);
    
    void updateDeliveryStatus(DeliveryId deliveryId, DeliveryStatus newStatus);
    
    void markDeliveryInTransit(DeliveryId deliveryId);
    
    void markDeliveryOutForDelivery(DeliveryId deliveryId);
    
    void markDeliveryDelivered(DeliveryId deliveryId);
    
    void markDeliveryFailed(DeliveryId deliveryId, String failureReason);
    
    void cancelDelivery(DeliveryId deliveryId, String cancellationReason);
    
    void addDeliveryNotes(DeliveryId deliveryId, String notes);
    
    List<Delivery> getOverdueDeliveries();
    
    List<Delivery> getDeliveriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
} 