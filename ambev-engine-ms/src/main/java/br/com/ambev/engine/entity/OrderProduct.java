package br.com.ambev.engine.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.Id;

public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;
}
