package iza.integracao.webhook;
import iza.integracao.service.BoletoCompensacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class AsaasWebhook {
    @Autowired
    private BoletoCompensacaoService service;
    @PostMapping("/asaas")
    public void compensarPagamento(@RequestBody Object payload){
       service.compensarAsaasBoleto(payload);
    }
}
