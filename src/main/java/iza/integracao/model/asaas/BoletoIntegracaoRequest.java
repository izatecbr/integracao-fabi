package iza.integracao.model.asaas;

import lombok.Data;

@Data
public class BoletoIntegracaoRequest {
    //codigo do cliente asaas
    private String customer;
    private String billingType = "BOLETO";
    //data de vencimento
    private String dueDate;
    //valor do boleto
    private Double value;
    //descrição do boleto
    private String description;
    //codigo unico gerado pelo sistema
    private String externalReference;
}
