package com.hadrion.identidadeacesso.aplicacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.identidadeacesso.aplicacao.comando.AlocarHospedeComando;
import com.hadrion.identidadeacesso.aplicacao.data.DescritorUsuarioData;
import com.hadrion.identidadeacesso.aplicacao.data.HospedeData;
import com.hadrion.identidadeacesso.dominio.identidade.AlocacaoHospedeService;
import com.hadrion.identidadeacesso.dominio.identidade.AutenticacaoService;
import com.hadrion.identidadeacesso.dominio.identidade.DescritorUsuario;
import com.hadrion.identidadeacesso.dominio.identidade.Email;
import com.hadrion.identidadeacesso.dominio.identidade.Hospede;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeRepositorio;
import com.hadrion.identidadeacesso.dominio.identidade.Usuario;
import com.hadrion.identidadeacesso.dominio.identidade.UsuarioRepositorio;

@Service
@Transactional
public class IdentidadeAplicacaoService {
	
	@Autowired
	private HospedeRepositorio hospedeRepositorio;
	
	@Autowired
	private AlocacaoHospedeService alocacaoHospedeService;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
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

	public DescritorUsuarioData autenticarUsuario(String hospedeId,
			String username, String senha) {
		
		DescritorUsuario usuario = autenticacaoService.autenticar(
				new HospedeId(hospedeId), username, senha);
		
		return usuario.ehDescritorNulo() ? null : 
			new DescritorUsuarioData(
					usuario.hospedeId().id(), 
					usuario.username(), 
					usuario.email(),
					true);
	}
	public DescritorUsuarioData obterUsuario(String hospedeId,
			String username) {
		
		Usuario usuario = usuarioRepositorio
				.usuarioComUsername(new HospedeId(hospedeId), username);
		
		return usuario == null ? null : 
			new DescritorUsuarioData(
					usuario.hospedeId().id(), 
					usuario.username(), 
					usuario.pessoa().email().endereco(),
					usuario.estaHabilitado());
	}
	
}
