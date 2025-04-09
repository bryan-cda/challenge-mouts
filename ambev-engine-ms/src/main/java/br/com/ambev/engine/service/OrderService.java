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

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Flux<Order> listOrders() {
        // Envolve a chamada bloqueante dentro de um Flux reativo
        return Flux.defer(() -> Flux.fromIterable(orderRepository.findAll()))  // Transformando a lista em Flux
                .onErrorResume(e -> {
                    // Se ocorrer uma exceção, retorna Flux vazio
                    return Flux.empty();
                });
    }

    // Retorna um pedido por código de forma reativa (usando Mono)
    public Mono<Order> findByCode(UUID code) {
        return Mono.just(orderRepository.findByCode(code));  // Chamando o método síncrono em um Mono
    }

    // Criando um pedido de forma reativa (usando Mono)
    public Mono<Order> createOrder(Order order) {
        // Envolvendo a chamada de repositório síncrona em um Mono para tornar assíncrono
        return Mono.just(orderRepository.save(order)) // Salva a Order de forma síncrona, mas envolve no Mono para tornar reativo
                .flatMap(savedOrder -> {
                    // Agora salva os Products individualmente
                    List<Mono<Product>> productMonos = order.getProducts().stream()
                            .map(product -> {
                                product.setOrder(savedOrder); // Associa a order ao product
                                return Mono.just(productRepository.save(product)); // Salva o Product e envolve no Mono
                            })
                            .collect(Collectors.toList());

                    // Espera todos os produtos serem salvos e então retorna a order salva
                    return Mono.when(productMonos) // Espera todos os saves de products
                            .then(Mono.just(savedOrder)); // Retorna a order salva após todos os produtos serem salvos
                })
                .onErrorResume(e -> {
                    // Em caso de erro, loga e retorna um erro customizado
                    log.error("Erro ao salvar a Order ou os Products", e);
                    return Mono.error(new RuntimeException("Falha ao criar o pedido."));
                });
    }

    public Mono<OrderTotalAmountRequest> findOrderByCodeAndCalculateTotal(UUID code) {
        // Envolvendo a chamada síncrona do repositório JPA em Mono.just()
        return Mono.just(orderRepository.findByCode(code)) // Envolvendo o resultado do findByCode em Mono
                .flatMap(order -> {
                    // Calculando o total de preços dos produtos da order
                    BigDecimal totalAmount = order.getProducts().stream()
                            .map(Product::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    // Criando o DTO com o total calculado
                    OrderTotalAmountRequest response = new OrderTotalAmountRequest(
                            order.getCode(),
                            order.getName(),
                            order.getCnpj(),
                            order.getEmail(),
                            order.getQuantity(),
                            order.getProducts(),
                            totalAmount
                    );

                    return Mono.just(response); // Retorna o DTO encapsulado em Mono
                })
                .onErrorResume(e -> {
                    // Logando o erro antes de retornar um Mono com erro
                    log.error("Erro ao calcular o total da ordem para o código: {}", code, e);

                    // Lançando uma exceção personalizada em caso de erro
                    return Mono.error(new OrderCalculationException(String.format("Erro ao calcular o total para o pedido: %s | Exception: %s", code, e)));
                });
    }
}