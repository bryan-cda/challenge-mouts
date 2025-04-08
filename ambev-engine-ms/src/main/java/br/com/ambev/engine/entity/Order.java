package br.com.ambev.engine.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tb_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private UUID code;
    private String name;
    private String cnpj;
    private String email;
    private Integer quantity;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_product")
    @JsonManagedReference
    private Product product;
}
