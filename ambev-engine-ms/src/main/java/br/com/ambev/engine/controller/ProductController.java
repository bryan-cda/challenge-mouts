package br.com.ambev.engine.controller;

import br.com.ambev.engine.entity.Product;
import br.com.ambev.engine.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "List all products", description = "Returns a list of all registered products.")
    @ApiResponse(responseCode = "200", description = "Products successfully retrieved")
    @GetMapping
    public Flux<Product> listProducts() {
        return productService.listProducts();
    }

    @Operation(summary = "Get a product by code", description = "Fetches a product using its UUID code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{code}")
    public Mono<ResponseEntity<Product>> listProductByCode(
            @Parameter(description = "UUID code of the product", required = true)
            @PathVariable UUID code) {
        return productService.findByCode(code)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new product", description = "Registers a new product using the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "500", description = "Internal server error while creating the product")
    })
    @PostMapping
    public Mono<ResponseEntity<Product>> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product object to be created",
                    required = true
            )
            @RequestBody Product product) {
        return productService.createProduct(product)
                .map(savedProduct -> ResponseEntity.status(201).body(savedProduct));
    }

    @Operation(summary = "Update an existing product", description = "Updates the product with the given ID using the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully updated"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated product object",
                    required = true
            )
            @RequestBody Product product) {
        return productService.updateProduct(id, product)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a product", description = "Deletes the product with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error while deleting the product")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(
            @Parameter(description = "ID of the product to delete", required = true)
            @PathVariable Long id) {
        productService.deleteProduct(id);
    }

}

