package iza.integracao.model.dto;
import lombok.Data;

@Data
public class BoletoRequest {
    private Integer empresa;
    private BoletoRequestPagador pagador;
    private BoletoRequestDetalhe detalhe;
}
