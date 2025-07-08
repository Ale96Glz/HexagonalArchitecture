package com.example.hexagonalorders.infrastructure.out.persistence;

import com.example.hexagonalorders.domain.model.Delivery;
import com.example.hexagonalorders.domain.model.DeliveryStatus;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;
import com.example.hexagonalorders.domain.port.out.DeliveryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación en memoria del repositorio de Delivery.
 * Esta es una implementación temporal para pruebas.
 */
@Repository
public class InMemoryDeliveryRepository implements DeliveryRepository {
    
    private final Map<String, Delivery> deliveries = new ConcurrentHashMap<>();

    @Override
    public Delivery save(Delivery delivery) {
        deliveries.put(delivery.getDeliveryId().value(), delivery);
        return delivery;
    }

    @Override
    public Optional<Delivery> findById(DeliveryId deliveryId) {
        return Optional.ofNullable(deliveries.get(deliveryId.value()));
    }

    @Override
    public List<Delivery> findAll() {
        return List.copyOf(deliveries.values());
    }

    @Override
    public List<Delivery> findByStatus(DeliveryStatus status) {
        return deliveries.values().stream()
                .filter(delivery -> delivery.getStatus() == status)
                .toList();
    }

    @Override
    public void deleteById(DeliveryId deliveryId) {
        deliveries.remove(deliveryId.value());
    }
} 