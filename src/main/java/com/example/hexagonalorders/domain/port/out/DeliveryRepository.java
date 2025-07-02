package com.example.hexagonalorders.domain.port.out;

import com.example.hexagonalorders.domain.model.Delivery;
import com.example.hexagonalorders.domain.model.DeliveryStatus;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;
import com.example.hexagonalorders.domain.model.valueobject.RouteId;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryPersonId;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface DeliveryRepository {
    
    
    Delivery save(Delivery delivery);
    
    /**
     * Encontrar una entrega por su ID.
     */
    Optional<Delivery> findById(DeliveryId deliveryId);
    
    /**
     * Encontrar todas las entregas para una ruta específica.
     */
    List<Delivery> findByRouteId(RouteId routeId);
    
    /**
     * Encontrar todas las entregas para un delivery person específico.
     */
    List<Delivery> findByDeliveryPersonId(DeliveryPersonId deliveryPersonId);
    
    /**
     * Encontrar todas las entregas para un número de orden específico.
     */
    List<Delivery> findByOrderNumber(OrderNumber orderNumber);
    
    /**
     * Encontrar todas las entregas con un estado específico.
     */
    List<Delivery> findByStatus(DeliveryStatus status);
    
    /**
     * Encontrar todas las entregas programadas para un rango de fechas específico.
     */
    List<Delivery> findByScheduledDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Encontrar todas las entregas vencidas (la fecha programada está en el pasado y el estado no es final).
     */
    List<Delivery> findOverdueDeliveries();
    
    /**
     * Eliminar una entrega del repositorio.
     */
    void delete(DeliveryId deliveryId);
    
    /**
     * Verificar si una entrega existe por su ID.
     */
    boolean existsById(DeliveryId deliveryId);
    
    /**
     * Contar el total de entregas.
     */
    long count();
    
    /**
     * Contar las entregas con un estado específico.
     */
    long countByStatus(DeliveryStatus status);
} 