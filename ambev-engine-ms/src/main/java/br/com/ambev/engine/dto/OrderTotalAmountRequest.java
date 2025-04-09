package br.com.ambev.engine.dto;

import br.com.ambev.engine.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderTotalAmountRequest {
    private UUID code;
    private String name;
    private String cnpj;
    private String email;
    private Integer quantity;
    private List<Product> products;
    private BigDecimal totalAmount;
}
