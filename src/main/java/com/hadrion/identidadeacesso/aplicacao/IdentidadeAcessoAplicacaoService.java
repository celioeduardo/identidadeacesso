package com.hadrion.identidadeacesso.aplicacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.identidadeacesso.aplicacao.comando.AlocarHospedeComando;
import com.hadrion.identidadeacesso.aplicacao.data.HospedeData;
import com.hadrion.identidadeacesso.dominio.identidade.AlocacaoHospedeService;
import com.hadrion.identidadeacesso.dominio.identidade.Email;
import com.hadrion.identidadeacesso.dominio.identidade.Hospede;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeRepositorio;

@Service
@Transactional
public class IdentidadeAcessoAplicacaoService {
	
	@Autowired
	private HospedeRepositorio hospedeRepositorio;
	
	@Autowired
	private AlocacaoHospedeService alocacaoHospedeService;
	
	@Transactional(readOnly=true)
	public HospedeData hospede(String hospedeId) {
		Hospede hospede = hospedeRepositorio.hospedeDoId(new HospedeId(hospedeId));
		
		return hospede != null ? construir(hospede) : null;
	}
	
	public HospedeData alocarHospede(AlocarHospedeComando comando){
		
		Hospede hospede = alocacaoHospedeService.alocarHospede(
				comando.getNome(), 
				comando.getDescricao(), 
				comando.getNomeAdministrador(), 
				new Email(comando.getEmailAdministrador()));
		
		return construir(hospede);
		
	}

	private HospedeData construir(Hospede hospede){
		return new HospedeData(
				hospede.hospedeId().id(), 
				hospede.nome(), 
				hospede.descricao(), 
				hospede.estaAtivo());
	}

	public List<HospedeData> hospedes() {
		
		List<HospedeData> result = new ArrayList<>();
		for (Hospede hospede : hospedeRepositorio.todos()) 
			result.add(construir(hospede));
		
		return result;
	}
	
}
