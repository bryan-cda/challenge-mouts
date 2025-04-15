package br.com.ambev.engine.controller;

import br.com.ambev.engine.entity.Product;
import br.com.ambev.engine.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(ProductController.class)
@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .code(UUID.randomUUID())
                .name("Cerveja Skol")
                .price(BigDecimal.valueOf(4.99))
                .build();
    }

    @Test
    void testListProducts() {
        when(productService.listProducts()).thenReturn(Flux.just(product));

        webTestClient.get()
                .uri("/ambev/v1/engine/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(1)
                .contains(product);
    }

    @Test
    void testListProductByCode_found() {
        when(productService.findByCode(product.getCode())).thenReturn(Mono.just(product));

        webTestClient.get()
                .uri("/ambev/v1/engine/products/{code}", product.getCode())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(product);
    }

    @Test
    void testListProductByCode_notFound() {
        when(productService.findByCode(any(UUID.class))).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/ambev/v1/engine/products/{code}", UUID.randomUUID())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateProduct() {
        when(productService.createProduct(any(Product.class))).thenReturn(Mono.just(product));

        webTestClient.post()
                .uri("/ambev/v1/engine/products")
                .bodyValue(product)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .isEqualTo(product);
    }

    @Test
    void testUpdateProduct_found() {
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(Mono.just(product));

        webTestClient.put()
                .uri("/ambev/v1/engine/products/{id}", 1L)
                .bodyValue(product)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(product);
    }

    @Test
    void testUpdateProduct_notFound() {
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(Mono.empty());

        webTestClient.put()
                .uri("/ambev/v1/engine/products/{id}", 1L)
                .bodyValue(product)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteProduct_found() {

        webTestClient.delete()
                .uri("/ambev/v1/engine/products/{id}", 1L)
                .exchange()
                .expectStatus().isNoContent();
    }

}
