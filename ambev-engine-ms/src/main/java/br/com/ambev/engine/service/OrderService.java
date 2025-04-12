package br.com.ambev.engine.service;

import br.com.ambev.engine.dto.OrderTotalAmountRequest;
import br.com.ambev.engine.entity.Order;
import br.com.ambev.engine.entity.Product;
import br.com.ambev.engine.exception.OrderCalculationException;
import br.com.ambev.engine.repository.OrderRepository;
import br.com.ambev.engine.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Flux<Order> listOrders() {
        return Flux.defer(() -> Flux.fromIterable(orderRepository.findAll()))
                .onErrorResume(e -> {
                    return Flux.empty();
                });
    }

    public Mono<Order> findByCode(UUID code) {
        return Mono.just(orderRepository.findByCode(code));
    }

    public Mono<Order> createOrder(Order order) {
        return Mono.just(orderRepository.save(order))
                .flatMap(savedOrder -> {
                    List<Mono<Product>> productMonos = order.getProducts().stream()
                            .map(product -> {
                                product.setOrder(savedOrder);
                                return Mono.just(productRepository.save(product));
                            })
                            .collect(Collectors.toList());

                    return Mono.when(productMonos)
                            .then(Mono.just(savedOrder));
                })
                .onErrorResume(e -> {
                    log.error("Erro ao salvar a Order ou os Products", e);
                    return Mono.error(new RuntimeException("Falha ao criar o pedido."));
                });
    }

    public Mono<OrderTotalAmountRequest> findOrderByCodeAndCalculateTotal(UUID code) {
        return Mono.just(orderRepository.findByCode(code))
                .flatMap(order -> {
                    BigDecimal totalAmount = order.getProducts().stream()
                            .map(Product::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    OrderTotalAmountRequest response = new OrderTotalAmountRequest(
                            order.getCode(),
                            order.getName(),
                            order.getCnpj(),
                            order.getEmail(),
                            order.getQuantity(),
                            order.getProducts(),
                            totalAmount
                    );

                    return Mono.just(response);
                })
                .onErrorResume(e -> {
                    log.error("Erro ao calcular o total da ordem para o código: {}", code, e);

                    return Mono.error(new OrderCalculationException(String.format("Erro ao calcular o total para o pedido: %s | Exception: %s", code, e)));
                });
    }
}