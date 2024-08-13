package iza.integracao.model.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class BoletoRequestDetalhe {
    private String codigoTitulo;
    private String numeroDocumento;
    private LocalDate dataVencimento;
    private Double valor;
    private String descricao;
}
