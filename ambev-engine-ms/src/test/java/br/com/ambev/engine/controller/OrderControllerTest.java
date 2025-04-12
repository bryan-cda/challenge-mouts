package br.com.ambev.engine.controller;

import br.com.ambev.engine.dto.OrderTotalAmountRequest;
import br.com.ambev.engine.entity.Order;
import br.com.ambev.engine.entity.Product;
import br.com.ambev.engine.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private OrderService orderService;

    private Order mockOrder;
    private UUID mockCode;

    @BeforeEach
    void setup() {
        mockCode = UUID.randomUUID();
        mockOrder = new Order();
        mockOrder.setCode(mockCode);
        mockOrder.setCnpj("00.623.904/0001-73");
        mockOrder.setQuantity(100);
        mockOrder.setName("Foo");
        mockOrder.setEmail("foo@bar.com");
        mockOrder.setProducts(null);

    }

    @Test
    void testListOrders() {
        when(orderService.listOrders()).thenReturn(Flux.just(mockOrder));

        webTestClient.get()
                .uri("/ambev/v1/engine/orders")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Order.class)
                .hasSize(1)
                .contains(mockOrder);
    }

    @Test
    void testListOrderByCodeFound() {
        when(orderService.findByCode(mockCode)).thenReturn(Mono.just(mockOrder));

        webTestClient.get()
                .uri("/ambev/v1/engine/orders/{code}", mockCode)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Order.class)
                .isEqualTo(mockOrder);
    }

    @Test
    void testListOrderByCodeNotFound() {
        when(orderService.findByCode(mockCode)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/ambev/v1/engine/orders/{code}", mockCode)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateOrder() {
        when(orderService.createOrder(any(Order.class))).thenReturn(Mono.just(mockOrder));

        webTestClient.post()
                .uri("/ambev/v1/engine/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mockOrder)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Order.class)
                .isEqualTo(mockOrder);
    }

    @Test
    void testGetOrderTotalAmountFound() {

        OrderTotalAmountRequest mockResponse = new OrderTotalAmountRequest(
                mockOrder.getCode(),
                mockOrder.getName(),
                mockOrder.getCnpj(),
                mockOrder.getEmail(),
                mockOrder.getQuantity(),
                mockOrder.getProducts(),
                BigDecimal.valueOf(1000)
        );

        when(orderService.findOrderByCodeAndCalculateTotal(mockCode)).thenReturn(Mono.just(mockResponse));

        webTestClient.get()
                .uri("/ambev/v1/engine/orders/{code}/total", mockCode)
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderTotalAmountRequest.class)
                .isEqualTo(mockResponse);
    }

    @Test
    void testGetOrderTotalAmountNotFound() {
        when(orderService.findOrderByCodeAndCalculateTotal(mockCode)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/ambev/v1/engine/orders/{code}/total", mockCode)
                .exchange()
                .expectStatus().isNotFound();
    }
}
