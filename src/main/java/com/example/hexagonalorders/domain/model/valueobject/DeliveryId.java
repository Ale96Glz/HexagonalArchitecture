package com.example.hexagonalorders.domain.model.valueobject;

public record DeliveryId(String value) {
    public DeliveryId {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la entrega no puede ser nulo o vacio");
        }
    }

    @Override
    public String toString() {
        return value;
    }
} 