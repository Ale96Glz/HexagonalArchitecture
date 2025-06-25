package com.example.hexagonalorders.infrastructure.in.web.dto;

import com.example.hexagonalorders.domain.model.DeliveryStatus;

import java.time.LocalDateTime;


public class DeliveryDto {
    private String deliveryId;
    private String routeId;
    private String deliveryPersonId;
    private AddressDto deliveryAddress;
    private String orderNumber;
    private LocalDateTime scheduledDate;
    private DeliveryStatus status;
    private LocalDateTime actualDeliveryDate;
    private String notes;

    public DeliveryDto() {}

    public DeliveryDto(String deliveryId, String routeId, String deliveryPersonId, 
                      AddressDto deliveryAddress, String orderNumber, LocalDateTime scheduledDate, 
                      DeliveryStatus status, LocalDateTime actualDeliveryDate, String notes) {
        this.deliveryId = deliveryId;
        this.routeId = routeId;
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryAddress = deliveryAddress;
        this.orderNumber = orderNumber;
        this.scheduledDate = scheduledDate;
        this.status = status;
        this.actualDeliveryDate = actualDeliveryDate;
        this.notes = notes;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(String deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public AddressDto getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(AddressDto deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public LocalDateTime getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDateTime actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
} 