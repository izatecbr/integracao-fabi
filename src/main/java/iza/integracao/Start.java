package iza.integracao;

import iza.integracao.model.BoletoEntity;
import iza.integracao.model.dto.BoletoRequest;
import iza.integracao.model.dto.BoletoRequestDetalhe;
import iza.integracao.model.dto.BoletoRequestPagador;
import iza.integracao.repository.BoletoRepository;
import iza.integracao.service.BoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class Start implements ApplicationRunner {
    @Autowired
    private BoletoService service;
    @Autowired
    private BoletoRepository boletoRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*
        BoletoCadastro cadastro = new BoletoCadastro();
        cadastro.setName("Gleyson Sampaio - Cliente");
        cadastro.setCpfCnpj("00432922326");

        integrador.cadastrar(cadastro);
         */

        /*BoletoIntegracaoRequest request = new BoletoIntegracaoRequest();
        request.setDescription("Boleto de Teste");
        request.setValue(10.00);
        request.setDueDate(LocalDate.of(2024,7,30).toString());
        request.setExternalReference(UUID.randomUUID().toString());
        request.setCustomer("cus_000087782767");
        */

        BoletoRequest request = new BoletoRequest();
        request.setEmpresa(1);
        BoletoRequestPagador pagador = new BoletoRequestPagador();
        pagador.setNome("Gleyson Sampaio - Cliente");
        pagador.setCpfCnpj("00432922326");
        pagador.setEmail("gleyson@iza.tec.br");
        pagador.setCodigoCliente(1);
        request.setPagador(pagador);

        BoletoRequestDetalhe detalhe = new BoletoRequestDetalhe();
        detalhe.setDescricao("Boleto de Teste Pagar via Pix" );
        detalhe.setValor(5.0);
        detalhe.setDataVencimento(LocalDate.now().plusDays(1));
        detalhe.setCodigoTitulo(""+LocalDate.now().getDayOfMonth());
        detalhe.setNumeroDocumento("123123");
        request.setDetalhe(detalhe);

        //service.integrarBoleto(request);

        BoletoEntity boletoCodigoTitulo = boletoRepository.findFirstByCodigoTituloAndSubstituidoFalse("18");
        System.out.println(boletoCodigoTitulo);
        System.out.println(boletoCodigoTitulo.getId());
        System.out.println(boletoCodigoTitulo.getCompensacao().getDataPagamento());
        System.out.println(boletoCodigoTitulo.getCompensacao().isBaixaRealizada());
        System.out.println(boletoCodigoTitulo.getIntegracao().getStatusRetorno());
        System.out.println(boletoCodigoTitulo.getCodigoTitulo());

    }
}
