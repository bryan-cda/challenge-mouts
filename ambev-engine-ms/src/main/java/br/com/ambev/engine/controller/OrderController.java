package br.com.ambev.engine.controller;

import br.com.ambev.engine.entity.Order;
import br.com.ambev.engine.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
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

    @Bean
    @RouterOperations({
            @RouterOperation(method = RequestMethod.GET, operation = @Operation(description = "List all orders", operationId = "listOrders", tags = "orders")),
            @RouterOperation(method = RequestMethod.POST, operation = @Operation(description = "Create a new order", operationId = "createOrder", tags = "orders"))
    })
    @GetMapping
    public Flux<Order> listOrders() {
        return orderService.listOrders();
    }

    @Operation(
            summary = "Get an order by code",
            description = "Returns a specific order using the provided UUID code."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order successfully found"),
            @ApiResponse(responseCode = "404", description = "Order not found with the provided code"),
            @ApiResponse(responseCode = "500", description = "Server error while processing the request")
    })
    @GetMapping("/{code}")
    public Mono<ResponseEntity<Order>> listOrderByCode(@PathVariable UUID code) {
        return orderService.findByCode(code)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new order",
            description = "Creates a new order with the data provided in the request body and returns the created order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid order data in request"),
            @ApiResponse(responseCode = "500", description = "Internal server error while creating the order")
    })
    @PostMapping
    public Mono<ResponseEntity<Order>> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order)
                .map(savedOrder -> ResponseEntity.status(201).body(savedOrder));
    }

    @Operation(
            summary = "Get total amount of an order",
            description = "Calculates and returns the total amount for a specific order based on its UUID code."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order total calculated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found with the provided code"),
            @ApiResponse(responseCode = "500", description = "Server error while calculating the order total")
    })
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
