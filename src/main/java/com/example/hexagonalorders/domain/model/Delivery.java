package com.example.hexagonalorders.domain.model;

import com.example.hexagonalorders.domain.event.DomainEvent;
import com.example.hexagonalorders.domain.event.DeliveryCreatedEvent;
import com.example.hexagonalorders.domain.event.DeliveryStatusChangedEvent;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;
import com.example.hexagonalorders.domain.model.valueobject.RouteId;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryPersonId;
import com.example.hexagonalorders.domain.model.valueobject.Address;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Delivery {
    private final DeliveryId deliveryId;
    private final RouteId routeId;
    private final DeliveryPersonId deliveryPersonId;
    private final Address deliveryAddress;
    private final OrderNumber orderNumber;
    private final LocalDateTime scheduledDate;
    private DeliveryStatus status;
    private LocalDateTime actualDeliveryDate;
    private String notes;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Delivery(DeliveryId deliveryId, RouteId routeId, DeliveryPersonId deliveryPersonId, 
                   Address deliveryAddress, OrderNumber orderNumber, LocalDateTime scheduledDate, 
                   DeliveryStatus status) {
        if (deliveryId == null) {
            throw new IllegalArgumentException("El ID de la entrega no puede ser nulo");
        }
        if (routeId == null) {
            throw new IllegalArgumentException("La ruta no puede ser nula");
        }
        if (deliveryPersonId == null) {
            throw new IllegalArgumentException("El ID del repartidor no puede ser nulo");
        }
        if (deliveryAddress == null) {
            throw new IllegalArgumentException("La direccion de entrega no puede ser nula");
        }
        if (orderNumber == null) {
            throw new IllegalArgumentException("El numero de orden no puede ser nulo");
        }
        if (scheduledDate == null) {
            throw new IllegalArgumentException("La fecha de entrega no puede ser nula");
        }
        if (status == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        
        this.deliveryId = deliveryId;
        this.routeId = routeId;
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryAddress = deliveryAddress;
        this.orderNumber = orderNumber;
        this.scheduledDate = scheduledDate;
        this.status = status;
        
        domainEvents.add(new DeliveryCreatedEvent(deliveryId, routeId, deliveryPersonId));
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

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderNumber getOrderNumber() {
        return orderNumber;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public LocalDateTime getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public String getNotes() {
        return notes;
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public void updateStatus(DeliveryStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser nulo");
        }
        
        DeliveryStatus oldStatus = this.status;
        this.status = newStatus;
        
        if (newStatus == DeliveryStatus.DELIVERED && this.actualDeliveryDate == null) {
            this.actualDeliveryDate = LocalDateTime.now();
        }
        
        domainEvents.add(new DeliveryStatusChangedEvent(deliveryId, oldStatus, newStatus));
    }

    public void markInTransit() {
        if (status != DeliveryStatus.SCHEDULED) {
            throw new IllegalStateException("La entrega debe estar en estado SCHEDULED para ser marcada como en transito");
        }
        updateStatus(DeliveryStatus.IN_TRANSIT);
    }


    public void markOutForDelivery() {
        if (status != DeliveryStatus.IN_TRANSIT) {
            throw new IllegalStateException("La entrega debe estar en estado IN_TRANSIT para ser marcada como fuera para entrega");
        }
        updateStatus(DeliveryStatus.OUT_FOR_DELIVERY);
    }

    public void markDelivered() {
        if (status != DeliveryStatus.OUT_FOR_DELIVERY) {
            throw new IllegalStateException("La entrega debe estar en estado OUT_FOR_DELIVERY para ser marcada como entregada");
        }
        updateStatus(DeliveryStatus.DELIVERED);
    }


    public void markFailed(String failureReason) {
        if (status != DeliveryStatus.OUT_FOR_DELIVERY && status != DeliveryStatus.IN_TRANSIT) {
            throw new IllegalStateException("La entrega debe estar en estado OUT_FOR_DELIVERY o IN_TRANSIT para ser marcada como fallida");
        }
        this.notes = failureReason;
        updateStatus(DeliveryStatus.FAILED);
    }

    public void cancel(String cancellationReason) {
        if (status == DeliveryStatus.DELIVERED || status == DeliveryStatus.CANCELLED) {
            throw new IllegalStateException("No se puede cancelar una entrega que ya ha sido entregada o cancelada");
        }
        this.notes = cancellationReason;
        updateStatus(DeliveryStatus.CANCELLED);
    }


    public void addNotes(String notes) {
        if (notes == null || notes.trim().isEmpty()) {
            throw new IllegalArgumentException("Las notas no pueden ser nulas o vacias");
        }
        this.notes = notes;
    }

    public boolean isOverdue() {
        return status != DeliveryStatus.DELIVERED && 
               status != DeliveryStatus.CANCELLED && 
               status != DeliveryStatus.FAILED &&
               LocalDateTime.now().isAfter(scheduledDate);
    }

    public boolean canBeReassigned() {
        return status == DeliveryStatus.SCHEDULED || status == DeliveryStatus.IN_TRANSIT;
    }


    public void reassignTo(DeliveryPersonId newDeliveryPersonId) {
        if (newDeliveryPersonId == null) {
            throw new IllegalArgumentException("El ID del repartidor no puede ser nulo");
        }
        if (!canBeReassigned()) {
            throw new IllegalStateException("La entrega no puede ser reasignada en el estado actual: " + status);
        }
    }
} 