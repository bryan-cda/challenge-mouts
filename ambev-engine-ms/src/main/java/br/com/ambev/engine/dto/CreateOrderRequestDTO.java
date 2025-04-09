package br.com.ambev.engine.dto;

import br.com.ambev.engine.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateOrderRequestDTO {
    private String name;
    private String cnpj;
    private String email;
    private List<Product> products;
    private Integer quantity;


}
