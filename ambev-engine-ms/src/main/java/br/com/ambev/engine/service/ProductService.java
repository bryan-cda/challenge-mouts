package br.com.ambev.engine.service;

import br.com.ambev.engine.entity.Product;
import br.com.ambev.engine.exception.DeleteProductException;
import br.com.ambev.engine.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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
                    if (optionalProduct.isPresent()) {
                        return Mono.fromRunnable(() -> productRepository.delete(optionalProduct.get()));
                    } else {
                        log.info("Produto não achado pelo id: {}", id);
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
                    }
                })
                .onErrorMap(e -> {
                    // Lida com erro inesperado e lança a exceção customizada
                    log.error("Erro ao tentar deletar o produto com id {}: {}", id, e.getMessage());
                    return new DeleteProductException("Erro ao deletar o produto, por favor, verifique a solicitação e tente novamente.");
                })
                .then(); // Retorna Mono<Void> com sucesso (no content)
    }
}
