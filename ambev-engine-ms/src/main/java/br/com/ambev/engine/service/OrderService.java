package br.com.ambev.engine.service;

import br.com.ambev.engine.entity.Order;
import br.com.ambev.engine.repository.OrderRepository;
import br.com.ambev.engine.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Flux<Order> listOrders() {
        return Flux.defer(() -> Flux.fromIterable(orderRepository.findAll()));  // Chamando o método síncrono em um Flux
    }

    // Retorna um pedido por código de forma reativa (usando Mono)
    public Mono<Order> findByCode(UUID code) {
        return Mono.defer(() -> Mono.justOrEmpty(orderRepository.findByCode(code)));  // Chamando o método síncrono em um Mono
    }

    // Criando um pedido de forma reativa (usando Mono)
    public Mono<Order> createOrder(Order order) {
        return Mono.defer(() -> Mono.just(orderRepository.save(order)));  // Chamando o método síncrono em um Mono
    }
}
