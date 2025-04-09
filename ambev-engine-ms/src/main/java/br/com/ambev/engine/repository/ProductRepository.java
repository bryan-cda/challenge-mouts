package br.com.ambev.engine.repository;

import br.com.ambev.engine.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByCode(UUID code);
}
