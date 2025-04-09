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
        return Flux.fromIterable(productRepository.findAll());// Retorna todos os produtos de forma reativa
    }

    public Mono<Product> findByCode(UUID code) {
        return Mono.just(productRepository.findByCode(code));  // Busca um produto por código
    }

    public Mono<Product> createProduct(Product product) {
        return Mono.just(productRepository.save(product));  // Cria um novo produto
    }

    public Mono<Product> updateProduct(Long id, Product product) {
        return Mono.fromCallable(() -> productRepository.findById(id))  // Envolvendo a operação de bloqueio com Mono
                .flatMap(existingProduct -> {
                    if (existingProduct.isPresent()) {
                        existingProduct.get().setName(product.getName());
                        existingProduct.get().setPrice(product.getPrice());
                        return Mono.fromCallable(() -> productRepository.save(existingProduct.get()));  // Salva de forma bloqueante, mas de forma reativa
                    } else {
                        return Mono.empty();  // Se o produto não for encontrado, retorna um Mono vazio
                    }
                });
    }

    public Mono<Void> deleteProduct(Long id) {
        return Mono.fromCallable(() -> productRepository.findById(id))  // Envolvendo a operação de bloqueio com Mono
                .flatMap(optionalProduct -> {
                    // Exclui o produto de forma bloqueante, mas de forma reativa
                    // Se o produto não for encontrado, retorna um Mono vazio
                    return optionalProduct.map(product -> Mono.fromRunnable(() -> productRepository.delete(product))).orElseGet(Mono::empty);
                }).then();  // Retorna Mono<Void> ao final da execução
    }
}
