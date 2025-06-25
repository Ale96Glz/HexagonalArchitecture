package com.example.hexagonalorders.application.service;

import com.example.hexagonalorders.domain.model.Order;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.domain.port.in.OrderUseCase;
import com.example.hexagonalorders.domain.port.out.OrderNumberGenerator;
import com.example.hexagonalorders.domain.port.out.OrderRepository;
import com.example.hexagonalorders.domain.service.OrderValidationService;
import com.example.hexagonalorders.infrastructure.in.web.mapper.OrderMapper.OrderCreationData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

/**
 * Application service implementing the order-related use cases.
 * This class orchestrates domain logic and coordinates with output ports.
 * It is part of the application layer and is responsible for:
 * - Implementing use cases defined by the domain
 * - Coordinating between domain objects and services
 * - Managing transactions and use case flow
 */
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderNumberGenerator orderNumberGenerator;
    private final OrderValidationService orderValidationService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Order createOrder(OrderCreationData orderData) {
        // Generate order number
        OrderNumber orderNumber = orderNumberGenerator.generate();
        
        // Create order with generated number
        Order order = new Order(
            orderNumber,
            orderData.getCustomerId(),
            orderData.getOrderDate(),
            orderData.getItems(),
            orderData.getStatus()
        );
        
        // Validate the order using the domain service
        orderValidationService.validateOrder(order);
        
        Order savedOrder = orderRepository.save(order);

        // Publish domain events
        savedOrder.getDomainEvents().forEach(eventPublisher::publishEvent);
        savedOrder.clearDomainEvents();

        return savedOrder;
    }

    @Override
    public Optional<Order> getOrder(OrderNumber orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(OrderNumber orderNumber) {
        orderRepository.deleteByOrderNumber(orderNumber);
    }
} 