package com.hadrion.identidadeacesso.dominio.acesso.papel;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.Version;

import com.hadrion.identidadeacesso.comum.Afirmacao;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.Usuario;
import com.hadrion.identidadeacesso.dominio.identidade.grupo.Grupo;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_PAPEL")
@Table(name="PAPEL")
public class Papel extends Afirmacao{
	
	@Embedded
	private HospedeId hospedeId;
	
	@Column(name="NOME",nullable=false)
	private String nome;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ID_GRUPO")
	private Grupo grupo;
	
	@Column(name="SUPORTA_ANINHAMENTO")
	private boolean suportaAninhamento;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
    @Version
    @Column(name="VERSAO")
    private int versaoConcorrencia;
	
	public Papel(HospedeId hospedeId, String nome, String descricao, 
			boolean suportaAninhamento) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.hospedeId = hospedeId;
		this.suportaAninhamento = suportaAninhamento;
		this.criarGrupoInterno();
	}
	
	@SuppressWarnings("unused")
	private Papel(){}
	
	public String nome(){
		return nome;
	}
	
	public String descricao(){
		return descricao;
	}
	
	public HospedeId hospedeId(){
		return hospedeId;
	}

	public void associarUsuario(Usuario usuario) {
		assertArgumentoNaoNulo(usuario, "Usuario deve não ser nulo.");
		assertArgumentoEquals(hospedeId(), usuario.hospedeId(), "Hospede errado para esse Usuario.");
		
		grupo().adicionarUsuario(usuario);
		
	}
	
	public Grupo grupo(){
		return this.grupo;
	}
	
	private void criarGrupoInterno(){
		 String nomeGrupo =
	                Grupo.PREFIXO_GRUPO_PAPEL
	                + UUID.randomUUID().toString().toUpperCase();

	        this.grupo = new Grupo(
	                this.hospedeId(),
	                nomeGrupo,
	                "Grupo gerado para o Papel: " + this.nome());
	}

	public boolean estaNoPapel(Usuario usuario) {
		return grupo().ehMembro(usuario);
	}
	
	public boolean suportaAninhamento(){
		return suportaAninhamento;
	}
	
	public void associarGrupo(Grupo grupo) {
		assertArgumentoVerdadeiro(suportaAninhamento(), "Esse papel não suporta aninhamento.");
		assertArgumentoNaoNulo(grupo, "Grupo não pode ser nulo.");
		assertArgumentoEquals(hospedeId(), grupo.hospedeId(), "Hospede errado para esse grupo.");
				
		grupo().adicionarGrupo(grupo);
		
	}
	

}
