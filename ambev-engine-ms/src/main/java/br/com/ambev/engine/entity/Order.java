package br.com.ambev.engine.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
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

    @NotNull(message = "The field code cannot be null.")
    private UUID code;

    @NotEmpty(message = "The field name cannot be empty.")
    private String name;

    @NotEmpty(message = "The field CNPJ cannot be empty")
    private String cnpj;

    private String email;

    @NotNull (message = "The field quantity cannot be null")
    private Integer quantity;

    @NotNull(message = "The field product cannot be null.")
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Product> products;
}
