package br.com.ambev.engine.controller;

import br.com.ambev.engine.dto.CreateOrderRequestDTO;
import br.com.ambev.engine.dto.FindOrderByCodeResponseDTO;
import br.com.ambev.engine.entity.Fulfillment;
import br.com.ambev.engine.dto.CreateOrderResponseDTO;
import br.com.ambev.engine.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("ambev/v1/engine")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Fulfillment> listOrders(){
        return orderService.listOrders();
    }

    @GetMapping("/{code}")
    public ResponseEntity<FindOrderByCodeResponseDTO> listOrderById(@PathVariable UUID code){
        return new ResponseEntity<>(orderService.findByCode(code), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO createOrderRequestDTO){
        return new ResponseEntity<>(orderService.createOrder(createOrderRequestDTO), HttpStatus.CREATED);
    }
}
