package br.com.ambev.engine.service;

import br.com.ambev.engine.entity.Fulfillment;
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

    public Flux<Fulfillment> listOrders() {
        return Flux.defer(() -> Flux.fromIterable(orderRepository.findAll()));  // Chamando o método síncrono em um Flux
    }

    // Retorna um pedido por código de forma reativa (usando Mono)
    public Mono<Fulfillment> findByCode(UUID code) {
        return Mono.defer(() -> Mono.justOrEmpty(orderRepository.findByCode(code)));  // Chamando o método síncrono em um Mono
    }

    // Criando um pedido de forma reativa (usando Mono)
    public Mono<Fulfillment> createOrder(Fulfillment fulfillment) {
        return Mono.defer(() -> Mono.just(orderRepository.save(fulfillment)));  // Chamando o método síncrono em um Mono
    }
}
