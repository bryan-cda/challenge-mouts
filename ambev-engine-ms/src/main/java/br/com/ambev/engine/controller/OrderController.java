package br.com.ambev.engine.controller;

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

    // Método reativo para buscar pedido por código
    @GetMapping("/{code}")
    public Mono<ResponseEntity<Order>> listOrderByCode(@PathVariable UUID code) {
        return orderService.findByCode(code)
                .map(order -> ResponseEntity.ok(order))
                .defaultIfEmpty(ResponseEntity.notFound().build());  // Retorna 404 caso não encontre o pedido
    }

    // Método reativo para criar um novo pedido
    @PostMapping
    public Mono<ResponseEntity<Order>> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order)
                .map(savedOrder -> ResponseEntity.status(201).body(savedOrder));  // Retorna 201 após criar o pedido
    }
}
