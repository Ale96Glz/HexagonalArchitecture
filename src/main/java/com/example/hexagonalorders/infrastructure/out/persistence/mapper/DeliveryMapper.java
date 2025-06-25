package com.example.hexagonalorders.infrastructure.out.persistence.mapper;

import com.example.hexagonalorders.domain.model.Delivery;
import com.example.hexagonalorders.domain.model.DeliveryStatus;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryPersonId;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.domain.model.valueobject.RouteId;
import com.example.hexagonalorders.domain.model.valueobject.Address;
import com.example.hexagonalorders.infrastructure.out.persistence.entity.DeliveryEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeliveryMapper {
    
    public Delivery toDomain(DeliveryEntity entity) {
        if (entity == null) {
            return null;
        }
        
        // Crear la dirección usando los campos de la entidad
        Address address = new Address(
            entity.getStreet() != null ? entity.getStreet() : "Dirección por defecto",
            entity.getCity() != null ? entity.getCity() : "Ciudad",
            entity.getPostalCode() != null ? entity.getPostalCode() : "12345",
            entity.getCountry() != null ? entity.getCountry() : "País"
        );
        
        return new Delivery(
            new DeliveryId(entity.getDeliveryId()),
            new RouteId(entity.getRouteId()),
            new DeliveryPersonId(entity.getDeliveryPersonId()),
            address,
            new OrderNumber(entity.getOrderNumber()),
            entity.getScheduledDate(),
            entity.getStatus()
        );
    }
    
    public DeliveryEntity toEntity(Delivery delivery) {
        if (delivery == null) {
            return null;
        }
        
        DeliveryEntity entity = new DeliveryEntity(
            delivery.getDeliveryId().value(),
            delivery.getOrderNumber().value(),
            delivery.getRouteId().value(),
            delivery.getDeliveryPersonId().value(),
            delivery.getDeliveryAddress().street(),
            delivery.getDeliveryAddress().city(),
            delivery.getDeliveryAddress().postalCode(),
            delivery.getDeliveryAddress().country(),
            delivery.getStatus(),
            delivery.getScheduledDate()
        );
        
        return entity;
    }
    
    public List<Delivery> toDomainList(List<DeliveryEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        
        return entities.stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
} 