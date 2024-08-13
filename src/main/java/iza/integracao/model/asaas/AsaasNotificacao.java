package iza.integracao.model.asaas;

import lombok.Data;

@Data
public class AsaasNotificacao {
    private String id;
    private boolean enabled=true;
    private boolean emailEnabledForProvider=false;
    private boolean smsEnabledForProvider=false;
    private boolean emailEnabledForCustomer=false;
    private boolean smsEnabledForCustomer=false;
    private boolean phoneCallEnabledForCustomer=false;
    private boolean whatsappEnabledForCustomer = false;
    public AsaasNotificacao(){

    }
    public AsaasNotificacao(String id){
        this.id= id;
    }
}
