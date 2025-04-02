package br.com.ambev.engine.repository;

import br.com.ambev.engine.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
