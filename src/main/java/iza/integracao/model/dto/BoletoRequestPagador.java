package iza.integracao.model.dto;

import lombok.Data;

@Data
public class BoletoRequestPagador {
    private Integer codigoCliente;
    private String cpfCnpj;
    private String nome;
    private String email;
}
