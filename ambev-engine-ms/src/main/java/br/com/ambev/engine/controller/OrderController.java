package br.com.ambev.engine.controller;

import br.com.ambev.engine.dto.OrderTotalAmountRequest;
import br.com.ambev.engine.entity.Order;
import br.com.ambev.engine.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("ambev/v1/engine/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public Flux<Order> listOrders() {
        return orderService.listOrders();
    }

    @GetMapping("/{code}")
    public Mono<ResponseEntity<Order>> listOrderByCode(@PathVariable UUID code) {
        return orderService.findByCode(code)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Order>> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order)
                .map(savedOrder -> ResponseEntity.status(201).body(savedOrder));  // Retorna 201 após criar o pedido
    }

    @GetMapping("/{code}/total")
    public Mono<ResponseEntity<?>> getOrderTotalAmount(@PathVariable UUID code) {
        return orderService.findOrderByCodeAndCalculateTotal(code)
                .map(orderTotalAmountRequest ->
                        orderTotalAmountRequest != null ?
                                ResponseEntity.ok(orderTotalAmountRequest) :
                                ResponseEntity.notFound().build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
