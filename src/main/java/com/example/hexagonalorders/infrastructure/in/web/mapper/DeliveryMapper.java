package com.example.hexagonalorders.infrastructure.in.web.mapper;

import com.example.hexagonalorders.domain.model.Delivery;
import com.example.hexagonalorders.domain.model.DeliveryStatus;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryAddress;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryDate;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.infrastructure.in.web.dto.DeliveryDto;
import com.example.hexagonalorders.infrastructure.in.web.dto.DeliveryAddressDto;
import org.springframework.stereotype.Component;

/**
 * Clase Mapper responsable de convertir entre entidades de dominio y DTOs para Delivery.
 * Esta clase es parte de la capa adaptadora y ayuda a mantener
 * la separación entre el modelo de dominio y la capa de API.
 */
@Component
public class DeliveryMapper {
    
    public DeliveryDto toDto(Delivery delivery) {
        if (delivery == null) {
            return null;
        }
        return new DeliveryDto(
            delivery.getDeliveryId().value(),
            delivery.getOrderNumber().value(),
            toAddressDto(delivery.getDeliveryAddress()),
            delivery.getScheduledDate().value(),
            delivery.getStatus().name(),
            delivery.getDeliveryNotes()
        );
    }
    
    public Delivery toDomain(DeliveryDto dto) {
        if (dto == null) {
            return null;
        }
        return new Delivery(
            new DeliveryId(dto.getDeliveryId()),
            new OrderNumber(dto.getOrderNumber()),
            toDomainAddress(dto.getDeliveryAddress()),
            new DeliveryDate(dto.getScheduledDate()),
            DeliveryStatus.valueOf(dto.getStatus()),
            dto.getDeliveryNotes()
        );
    }
    
    private DeliveryAddressDto toAddressDto(DeliveryAddress address) {
        if (address == null) {
            return null;
        }
        return new DeliveryAddressDto(
            address.getStreet(),
            address.getCity(),
            address.getState(),
            address.getPostalCode(),
            address.getCountry()
        );
    }
    
    private DeliveryAddress toDomainAddress(DeliveryAddressDto dto) {
        if (dto == null) {
            return null;
        }
        return new DeliveryAddress(
            dto.getStreet(),
            dto.getCity(),
            dto.getState(),
            dto.getPostalCode(),
            dto.getCountry()
        );
    }
} 