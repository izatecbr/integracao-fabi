package iza.integracao.model.asaas;

import java.util.ArrayList;
import java.util.List;

public class AsaasNotificacaoLote {
    private String customer;
    private List<AsaasNotificacao> notifications = new ArrayList<>();

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<AsaasNotificacao> getNotifications() {
        return notifications;
    }

    public String getCustomer() {
        return customer;
    }
}
