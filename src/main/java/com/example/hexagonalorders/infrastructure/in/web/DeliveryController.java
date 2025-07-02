package com.example.hexagonalorders.infrastructure.in.web;

import com.example.hexagonalorders.domain.model.Delivery;
import com.example.hexagonalorders.domain.model.DeliveryStatus;
import com.example.hexagonalorders.domain.port.in.DeliveryUseCase;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;
import com.example.hexagonalorders.domain.model.valueobject.RouteId;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryPersonId;
import com.example.hexagonalorders.domain.model.valueobject.Address;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.infrastructure.in.web.dto.DeliveryDto;
import com.example.hexagonalorders.infrastructure.in.web.dto.AddressDto;
import com.example.hexagonalorders.infrastructure.in.web.mapper.DeliveryMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for delivery management operations.
 */
@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    
    private final DeliveryUseCase deliveryUseCase;
    
    public DeliveryController(DeliveryUseCase deliveryUseCase) {
        this.deliveryUseCase = deliveryUseCase;
    }
    
    /**
     * Creates a new delivery.
     */
    @PostMapping
    public ResponseEntity<DeliveryDto> createDelivery(@RequestBody DeliveryDto deliveryDto) {
        try {
            DeliveryId deliveryId = new DeliveryId(deliveryDto.getDeliveryId());
            RouteId routeId = new RouteId(deliveryDto.getRouteId());
            DeliveryPersonId deliveryPersonId = new DeliveryPersonId(deliveryDto.getDeliveryPersonId());
            OrderNumber orderNumber = new OrderNumber(deliveryDto.getOrderNumber());
            
            Address address = new Address(
                deliveryDto.getDeliveryAddress().getStreet(),
                deliveryDto.getDeliveryAddress().getCity(),
                deliveryDto.getDeliveryAddress().getPostalCode(),
                deliveryDto.getDeliveryAddress().getCountry()
            );
            
            Delivery delivery = deliveryUseCase.createDelivery(
                deliveryId, routeId, deliveryPersonId, address, orderNumber, deliveryDto.getScheduledDate()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(DeliveryMapper.toDto(delivery));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Retrieves a delivery by its ID.
     */
    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryDto> getDeliveryById(@PathVariable String deliveryId) {
        try {
            DeliveryId id = new DeliveryId(deliveryId);
            return deliveryUseCase.getDeliveryById(id)
                    .map(delivery -> ResponseEntity.ok(DeliveryMapper.toDto(delivery)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Retrieves all deliveries for a specific route.
     */
    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<DeliveryDto>> getDeliveriesByRoute(@PathVariable String routeId) {
        try {
            RouteId id = new RouteId(routeId);
            List<DeliveryDto> deliveries = deliveryUseCase.getDeliveriesByRoute(id)
                    .stream()
                    .map(DeliveryMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(deliveries);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Retrieves all deliveries assigned to a specific delivery person.
     */
    @GetMapping("/delivery-person/{deliveryPersonId}")
    public ResponseEntity<List<DeliveryDto>> getDeliveriesByDeliveryPerson(@PathVariable String deliveryPersonId) {
        try {
            DeliveryPersonId id = new DeliveryPersonId(deliveryPersonId);
            List<DeliveryDto> deliveries = deliveryUseCase.getDeliveriesByDeliveryPerson(id)
                    .stream()
                    .map(DeliveryMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(deliveries);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Retrieves all deliveries for a specific order.
     */
    @GetMapping("/order/{orderNumber}")
    public ResponseEntity<List<DeliveryDto>> getDeliveriesByOrder(@PathVariable String orderNumber) {
        try {
            OrderNumber orderNum = new OrderNumber(orderNumber);
            List<DeliveryDto> deliveries = deliveryUseCase.getDeliveriesByOrder(orderNum)
                    .stream()
                    .map(DeliveryMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(deliveries);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Retrieves all deliveries with a specific status.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<DeliveryDto>> getDeliveriesByStatus(@PathVariable DeliveryStatus status) {
        List<DeliveryDto> deliveries = deliveryUseCase.getDeliveriesByStatus(status)
                .stream()
                .map(DeliveryMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(deliveries);
    }
    
    /**
     * Updates the status of a delivery.
     */
    @PutMapping("/{deliveryId}/status")
    public ResponseEntity<Void> updateDeliveryStatus(@PathVariable String deliveryId, 
                                                   @RequestParam DeliveryStatus status) {
        try {
            DeliveryId id = new DeliveryId(deliveryId);
            deliveryUseCase.updateDeliveryStatus(id, status);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Marks a delivery as in transit.
     */
    @PutMapping("/{deliveryId}/in-transit")
    public ResponseEntity<Void> markDeliveryInTransit(@PathVariable String deliveryId) {
        try {
            DeliveryId id = new DeliveryId(deliveryId);
            deliveryUseCase.markDeliveryInTransit(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Marks a delivery as out for delivery.
     */
    @PutMapping("/{deliveryId}/out-for-delivery")
    public ResponseEntity<Void> markDeliveryOutForDelivery(@PathVariable String deliveryId) {
        try {
            DeliveryId id = new DeliveryId(deliveryId);
            deliveryUseCase.markDeliveryOutForDelivery(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Marks a delivery as delivered.
     */
    @PutMapping("/{deliveryId}/delivered")
    public ResponseEntity<Void> markDeliveryDelivered(@PathVariable String deliveryId) {
        try {
            DeliveryId id = new DeliveryId(deliveryId);
            deliveryUseCase.markDeliveryDelivered(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Marks a delivery as failed.
     */
    @PutMapping("/{deliveryId}/failed")
    public ResponseEntity<Void> markDeliveryFailed(@PathVariable String deliveryId, 
                                                 @RequestParam String failureReason) {
        try {
            DeliveryId id = new DeliveryId(deliveryId);
            deliveryUseCase.markDeliveryFailed(id, failureReason);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Cancels a delivery.
     */
    @PutMapping("/{deliveryId}/cancel")
    public ResponseEntity<Void> cancelDelivery(@PathVariable String deliveryId, 
                                             @RequestParam String cancellationReason) {
        try {
            DeliveryId id = new DeliveryId(deliveryId);
            deliveryUseCase.cancelDelivery(id, cancellationReason);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Adds notes to a delivery.
     */
    @PutMapping("/{deliveryId}/notes")
    public ResponseEntity<Void> addDeliveryNotes(@PathVariable String deliveryId, 
                                               @RequestParam String notes) {
        try {
            DeliveryId id = new DeliveryId(deliveryId);
            deliveryUseCase.addDeliveryNotes(id, notes);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Retrieves all overdue deliveries.
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<DeliveryDto>> getOverdueDeliveries() {
        List<DeliveryDto> deliveries = deliveryUseCase.getOverdueDeliveries()
                .stream()
                .map(DeliveryMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(deliveries);
    }
    
    /**
     * Retrieves all deliveries scheduled for a specific date range.
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<DeliveryDto>> getDeliveriesByDateRange(
            @RequestParam LocalDateTime startDate, 
            @RequestParam LocalDateTime endDate) {
        List<DeliveryDto> deliveries = deliveryUseCase.getDeliveriesByDateRange(startDate, endDate)
                .stream()
                .map(DeliveryMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(deliveries);
    }
} 