package br.com.ambev.engine.controller;

import br.com.ambev.engine.domain.Orders;
import br.com.ambev.engine.dto.CreateOrderRequest;
import br.com.ambev.engine.dto.OrderResponse;
import br.com.ambev.engine.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ambev/v1/engine")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> listOrders(){
        return orderService.listOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> listOrderById(@PathVariable Long id){
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody Orders order){
        orderService.createOrder(order);
    }
}
