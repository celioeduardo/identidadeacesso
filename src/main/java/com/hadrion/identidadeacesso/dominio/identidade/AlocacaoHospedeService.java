package com.hadrion.identidadeacesso.dominio.identidade;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlocacaoHospedeService {
	
	@Autowired
	private HospedeRepositorio hospedeRepositorio;
	
	@Autowired
	private SenhaService senhaService;
	
	public Hospede alocarHospede(String nome,
			String descricao, String nomeAdministrador, Email emailAdministrador) {
		
		Hospede hospede = new Hospede(
				new HospedeId(UUID.randomUUID().toString().toLowerCase()),
				nome,descricao,true);
		
		hospedeRepositorio.salvar(hospede);
		
		registrarAdministradorPara(hospede, nomeAdministrador, emailAdministrador);

//	TODO Enviar evento TenantProvisioned
//		  DomainEventPublisher
//          .instance()
//          .publish(new TenantProvisioned(
//                  tenant.tenantId()));
		
		return hospede;
		
	}
	
	private void registrarAdministradorPara(
            Hospede hospede,
            String nomeAdministrador,
            Email email) {
		
		ConviteRegistro convite = hospede
				.oferecerConviteRegistro("inicio").perpetuo();
		
        String senhaForte = senhaService.gerarSenhaForte();
        
        Usuario admin = hospede.registrarUsuario(
        		convite.conviteId(), 
        		"admin", 
        		senhaForte, 
        		Ativacao.ativacaoIndeterminada(),
        		new Pessoa(
        				hospede.hospedeId(), 
        				nomeAdministrador, 
        				new InformacaoContato(email)));
        
        hospede.retirarConvite(convite.conviteId());
        
        hospedeRepositorio.salvar(hospede);
        
        
//  TODO Role de administrador       
//        Role adminRole =
//            hospede.provisionRole(
//                    "Administrator",
//                    "Default " + hospede.name() + " administrator.");
//
//        adminRole.assignUser(admin);
//
//        this.roleRepository().add(adminRole);
        
//	TODO Enviar evento TenantAdministratorRegistered
//        DomainEventPublisher.instance().publish(
//                new TenantAdministratorRegistered(
//                        hospede.tenantId(),
//                        hospede.name(),
//                        nomeAdministrador,
//                        anEmailAddress,
//                        admin.username(),
//                        senhaForte));
    }

}
