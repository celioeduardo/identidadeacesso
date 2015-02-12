package com.hadrion.identidadeacesso.dominio.identidade;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

public class AlocacaoHospedeService {
	
	@Autowired
	HospedeRepositorio hospedeRepositorio;
	
	public static Hospede alocarHospede(String nome,
			String descricao, String nomeAdministrador, Email emailAdministrador) {
		
		return new Hospede(
				new HospedeId(UUID.randomUUID().toString().toLowerCase()),
				nome,descricao,true);
		
	}

}
