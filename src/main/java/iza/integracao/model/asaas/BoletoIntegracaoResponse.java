package iza.integracao.model.asaas;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BoletoIntegracaoResponse extends BoletoIntegracaoRequest {
    public static final String RECEIVED="RECEIVED";
    private String id;
    //link do boleto
    private String bankSlipUrl;
    //ver quais status para a regra
    private String status;
    //codigo do banco
    private String nossoNumero;
    private boolean deleted;
    //valor pago
    private Double netValue;
    private String billingType;
    //data de pagamento
    private LocalDate clientPaymentDate;
    //data de compensacao
    private LocalDate paymentDate;
}
