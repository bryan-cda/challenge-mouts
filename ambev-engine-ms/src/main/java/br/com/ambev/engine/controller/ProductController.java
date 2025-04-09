package br.com.ambev.engine.controller;

import br.com.ambev.engine.entity.Product;
import br.com.ambev.engine.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("ambev/v1/engine/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<Product> listProducts() {
        return productService.listProducts();
    }

    @GetMapping("/{code}")
    public Mono<ResponseEntity<Product>> listProductByCode(@PathVariable UUID code) {
        return productService.findByCode(code)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());  // Retorna 404 caso não encontre o produto
    }

    @PostMapping
    public Mono<ResponseEntity<Product>> createProduct(@RequestBody Product product) {
        return productService.createProduct(product)
                .map(savedProduct -> ResponseEntity.status(201).body(savedProduct));  // Retorna 201 após criar o produto
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product)
                .map(ResponseEntity::ok)  // Retorna 200 após atualizar o produto
                .defaultIfEmpty(ResponseEntity.notFound().build());  // Retorna 404 caso o produto não seja encontrado
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id)
                .map(aVoid -> ResponseEntity.noContent().build()) // Retorna 204 após excluir o produto
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Retorna 404 caso o produto não seja encontrado
    }

}

