package com.example.hexagonalorders.infrastructure.out.persistence.repository;

import com.example.hexagonalorders.domain.model.Delivery;
import com.example.hexagonalorders.domain.model.DeliveryStatus;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryId;
import com.example.hexagonalorders.domain.model.valueobject.DeliveryPersonId;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.domain.model.valueobject.RouteId;
import com.example.hexagonalorders.domain.port.out.DeliveryRepository;
import com.example.hexagonalorders.infrastructure.out.persistence.mapper.DeliveryMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DeliveryRepositoryAdapter implements DeliveryRepository {
    
    private final DeliveryJpaRepository jpaRepository;
    private final DeliveryMapper mapper;
    
    public DeliveryRepositoryAdapter(DeliveryJpaRepository jpaRepository, DeliveryMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Delivery save(Delivery delivery) {
        var entity = mapper.toEntity(delivery);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Delivery> findById(DeliveryId deliveryId) {
        return jpaRepository.findByDeliveryId(deliveryId.value())
            .map(mapper::toDomain);
    }
    
    @Override
    public List<Delivery> findByRouteId(RouteId routeId) {
        var entities = jpaRepository.findByRouteId(routeId.value());
        return mapper.toDomainList(entities);
    }
    
    @Override
    public List<Delivery> findByDeliveryPersonId(DeliveryPersonId deliveryPersonId) {
        var entities = jpaRepository.findByDeliveryPersonId(deliveryPersonId.value());
        return mapper.toDomainList(entities);
    }
    
    @Override
    public List<Delivery> findByOrderNumber(OrderNumber orderNumber) {
        var entities = jpaRepository.findByOrderNumber(orderNumber.value());
        return mapper.toDomainList(entities);
    }
    
    @Override
    public List<Delivery> findByStatus(DeliveryStatus status) {
        var entities = jpaRepository.findByStatus(status);
        return mapper.toDomainList(entities);
    }
    
    @Override
    public List<Delivery> findByScheduledDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        var entities = jpaRepository.findByScheduledDateBetween(startDate, endDate);
        return mapper.toDomainList(entities);
    }
    
    @Override
    public List<Delivery> findOverdueDeliveries() {
        var entities = jpaRepository.findOverdueDeliveries(LocalDateTime.now());
        return mapper.toDomainList(entities);
    }
    
    @Override
    public void delete(DeliveryId deliveryId) {
        jpaRepository.findByDeliveryId(deliveryId.value())
            .ifPresent(jpaRepository::delete);
    }
    
    @Override
    public boolean existsById(DeliveryId deliveryId) {
        return jpaRepository.existsByDeliveryId(deliveryId.value());
    }
    
    @Override
    public long count() {
        return jpaRepository.count();
    }
    
    @Override
    public long countByStatus(DeliveryStatus status) {
        return jpaRepository.countByStatus(status);
    }
} 