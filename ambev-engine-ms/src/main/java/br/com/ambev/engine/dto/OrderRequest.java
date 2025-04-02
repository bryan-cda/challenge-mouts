package br.com.ambev.engine.dto;

import br.com.ambev.engine.enumerations.ClientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private String firstName;
    private String lastName;
    private String cpf;
    private String cnpj;
    private ClientType clientType;
    private String productCode;
    private Integer quantity;
}
