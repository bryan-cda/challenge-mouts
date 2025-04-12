package br.com.ambev.engine.service;

import br.com.ambev.engine.entity.Product;
import br.com.ambev.engine.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<Product> listProducts() {
        return Flux.fromIterable(productRepository.findAll());
    }

    public Mono<Product> findByCode(UUID code) {
        return Mono.just(productRepository.findByCode(code));
    }

    public Mono<Product> createProduct(Product product) {
        return Mono.just(productRepository.save(product));
    }

    public Mono<Product> updateProduct(Long id, Product product) {
        return Mono.fromCallable(() -> productRepository.findById(id))
                .flatMap(existingProduct -> {
                    if (existingProduct.isPresent()) {
                        existingProduct.get().setName(product.getName());
                        existingProduct.get().setPrice(product.getPrice());
                        return Mono.fromCallable(() -> productRepository.save(existingProduct.get()));
                    } else {
                        return Mono.empty();
                    }
                });
    }

    public Mono<Void> deleteProduct(Long id) {
        return Mono.fromCallable(() -> productRepository.findById(id))
                .flatMap(optionalProduct -> {
                    return optionalProduct.map(product -> Mono.fromRunnable(() -> productRepository.delete(product))).orElseGet(Mono::empty);
                }).then();
    }
}
