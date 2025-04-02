package br.com.ambev.engine.repository;

import br.com.ambev.engine.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
