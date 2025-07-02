package com.example.hexagonalorders.infrastructure.in.web.mapper;

import com.example.hexagonalorders.domain.model.Delivery;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;
import com.example.hexagonalorders.domain.model.valueobject.RouteId;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryPersonId;
import com.example.hexagonalorders.domain.model.valueobject.Address;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.infrastructure.in.web.dto.DeliveryDto;
import com.example.hexagonalorders.infrastructure.in.web.dto.AddressDto;


public class DeliveryMapper {
    
    public static DeliveryDto toDto(Delivery delivery) {
        if (delivery == null) {
            return null;
        }
        
        AddressDto addressDto = new AddressDto(
            delivery.getDeliveryAddress().street(),
            delivery.getDeliveryAddress().city(),
            delivery.getDeliveryAddress().postalCode(),
            delivery.getDeliveryAddress().country()
        );
        
        return new DeliveryDto(
            delivery.getDeliveryId().value(),
            delivery.getRouteId().value(),
            delivery.getDeliveryPersonId().value(),
            addressDto,
            delivery.getOrderNumber().value(),
            delivery.getScheduledDate(),
            delivery.getStatus(),
            delivery.getActualDeliveryDate(),
            delivery.getNotes()
        );
    }
    

    public static Delivery toDomain(DeliveryDto deliveryDto) {
        if (deliveryDto == null) {
            return null;
        }
        
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
        
        return new Delivery(
            deliveryId,
            routeId,
            deliveryPersonId,
            address,
            orderNumber,
            deliveryDto.getScheduledDate(),
            deliveryDto.getStatus()
        );
    }
} 