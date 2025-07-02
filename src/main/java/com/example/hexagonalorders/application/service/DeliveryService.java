package com.example.hexagonalorders.application.service;

import com.example.hexagonalorders.domain.model.Delivery;
import com.example.hexagonalorders.domain.model.DeliveryStatus;
import com.example.hexagonalorders.domain.port.in.DeliveryUseCase;
import com.example.hexagonalorders.domain.port.out.DeliveryRepository;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;
import com.example.hexagonalorders.domain.model.valueobject.RouteId;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryPersonId;
import com.example.hexagonalorders.domain.model.valueobject.Address;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DeliveryService implements DeliveryUseCase {
    
    private final DeliveryRepository deliveryRepository;
    
    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }
    
    @Override
    public Delivery createDelivery(DeliveryId deliveryId, RouteId routeId, DeliveryPersonId deliveryPersonId,
                                 Address deliveryAddress, OrderNumber orderNumber, LocalDateTime scheduledDate) {
        if (deliveryRepository.existsById(deliveryId)) {
            throw new IllegalArgumentException("Delivery with ID " + deliveryId + " already exists");
        }
        
        Delivery delivery = new Delivery(deliveryId, routeId, deliveryPersonId, deliveryAddress, 
                                       orderNumber, scheduledDate, DeliveryStatus.SCHEDULED);
        
        return deliveryRepository.save(delivery);
    }
    
    @Override
    public Optional<Delivery> getDeliveryById(DeliveryId deliveryId) {
        return deliveryRepository.findById(deliveryId);
    }
    
    @Override
    public List<Delivery> getDeliveriesByRoute(RouteId routeId) {
        return deliveryRepository.findByRouteId(routeId);
    }
    
    @Override
    public List<Delivery> getDeliveriesByDeliveryPerson(DeliveryPersonId deliveryPersonId) {
        return deliveryRepository.findByDeliveryPersonId(deliveryPersonId);
    }
    
    @Override
    public List<Delivery> getDeliveriesByOrder(OrderNumber orderNumber) {
        return deliveryRepository.findByOrderNumber(orderNumber);
    }
    
    @Override
    public List<Delivery> getDeliveriesByStatus(DeliveryStatus status) {
        return deliveryRepository.findByStatus(status);
    }
    
    @Override
    public void updateDeliveryStatus(DeliveryId deliveryId, DeliveryStatus newStatus) {
        Delivery delivery = findDeliveryOrThrow(deliveryId);
        delivery.updateStatus(newStatus);
        deliveryRepository.save(delivery);
    }
    
    @Override
    public void markDeliveryInTransit(DeliveryId deliveryId) {
        Delivery delivery = findDeliveryOrThrow(deliveryId);
        delivery.markInTransit();
        deliveryRepository.save(delivery);
    }
    
    @Override
    public void markDeliveryOutForDelivery(DeliveryId deliveryId) {
        Delivery delivery = findDeliveryOrThrow(deliveryId);
        delivery.markOutForDelivery();
        deliveryRepository.save(delivery);
    }
    
    @Override
    public void markDeliveryDelivered(DeliveryId deliveryId) {
        Delivery delivery = findDeliveryOrThrow(deliveryId);
        delivery.markDelivered();
        deliveryRepository.save(delivery);
    }
    
    @Override
    public void markDeliveryFailed(DeliveryId deliveryId, String failureReason) {
        Delivery delivery = findDeliveryOrThrow(deliveryId);
        delivery.markFailed(failureReason);
        deliveryRepository.save(delivery);
    }
    
    @Override
    public void cancelDelivery(DeliveryId deliveryId, String cancellationReason) {
        Delivery delivery = findDeliveryOrThrow(deliveryId);
        delivery.cancel(cancellationReason);
        deliveryRepository.save(delivery);
    }
    
    @Override
    public void addDeliveryNotes(DeliveryId deliveryId, String notes) {
        Delivery delivery = findDeliveryOrThrow(deliveryId);
        delivery.addNotes(notes);
        deliveryRepository.save(delivery);
    }
    
    @Override
    public List<Delivery> getOverdueDeliveries() {
        return deliveryRepository.findOverdueDeliveries();
    }
    
    @Override
    public List<Delivery> getDeliveriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return deliveryRepository.findByScheduledDateBetween(startDate, endDate);
    }
    
    private Delivery findDeliveryOrThrow(DeliveryId deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery with ID " + deliveryId + " not found"));
    }
} 