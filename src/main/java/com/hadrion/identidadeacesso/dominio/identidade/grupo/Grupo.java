package com.hadrion.identidadeacesso.dominio.identidade.grupo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.data.annotation.Version;

import com.hadrion.identidadeacesso.comum.Afirmacao;
import com.hadrion.identidadeacesso.dominio.identidade.DominioRegistro;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.Usuario;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_GRUPO")
@Table(name="GRUPO")
public class Grupo extends Afirmacao{
	
	public static final String PREFIXO_GRUPO_PAPEL = "GRUPO-INTERNO-PAPEL: ";

	@Embedded
	private HospedeId hospedeId;
	
	@Column(name="NOME",nullable=false)
	private String nome;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@ElementCollection
	@CollectionTable(name="GRUPO_MEMBROS")
	private Set<MembroGrupo> membros;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
    @Version
    @Column(name="VERSAO")
    private int versaoConcorrencia;
    
    public Grupo(HospedeId hospedeId, String nome, String descricao) {
		super();
		this.hospedeId = hospedeId;
		this.nome = nome;
		this.descricao = descricao;
	}
    
    @SuppressWarnings("unused")
	private Grupo(){}

	public String nome(){
		return nome;
	}
	
	public String descricao(){
		return descricao;
	}
	
	public HospedeId hospedeId(){
		return hospedeId;
	}

	public void adicionarGrupo(Grupo grupo) {
		assertArgumentoNaoNulo(grupo, "Grupo não pode ser nulo.");
		assertArgumentoEquals(hospedeId(), grupo.hospedeId(), "Hospede errado para esse grupo.");
		assertArgumentoFalso(
				DominioRegistro.membroGrupoService()
					.ehMembroDoGrupo(grupo, this.paraMembroGrupo()), "Recursão no Grupo");
		getMembros().add(grupo.paraMembroGrupo());
	}
	
	public void adicionarUsuario(Usuario usuario) {
		assertArgumentoNaoNulo(usuario, "Usuário não pode ser nulo.");
		assertArgumentoEquals(hospedeId(), usuario.hospedeId(), "Hospede errado para esse usuário.");
		assertArgumentoVerdadeiro(usuario.estaHabilitado(), "Usuário está desabilitado.");
		
		getMembros().add(usuario.paraMembroGrupo());
		
	}

	protected boolean ehGrupoInterno() {
        return this.ehGrupoInterno(this.nome());
    }

    protected boolean ehGrupoInterno(String nome) {
        return nome.startsWith(PREFIXO_GRUPO_PAPEL);
    }
	
	private MembroGrupo paraMembroGrupo() {
		return new MembroGrupo(hospedeId(), nome, TipoMembro.Grupo);
	}

	Set<MembroGrupo> membros(){
		return Collections.unmodifiableSet(getMembros());
	}

	public int quantidadeMembros() {
		return getMembros().size();
	}
	
	private Set<MembroGrupo> getMembros(){
		if (membros == null)
			membros = new HashSet<MembroGrupo>();
		return membros;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Grupo objetoTipado = (Grupo) objeto;
			objetosIguais = new EqualsBuilder()
				.append(hospedeId(),objetoTipado.hospedeId())
				.append(nome(),objetoTipado.nome())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(135,27)
			.append(hospedeId())
			.append(nome())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Grupo [hospedeId=" + hospedeId()
				+ ",nome=" + nome()
				+ "]";
	}

	public boolean ehMembro(Usuario usuario) {
		this.assertArgumentoNaoNulo(usuario, "Usuário não pode ser nulo.");
        this.assertArgumentoEquals(this.hospedeId(), usuario.hospedeId(), "Hóspede errado para esse grupo.");
        this.assertArgumentoVerdadeiro(usuario.estaHabilitado(), "Usuário não está habilitado.");

        boolean ehMembro =
            getMembros().contains(usuario.paraMembroGrupo());

        if (ehMembro) {
            ehMembro = DominioRegistro.membroGrupoService()
            		.confirmarUsuario(this,usuario);
        } else {
            ehMembro = DominioRegistro.membroGrupoService()
            		.usuarioEstaEmGrupoAninhado(this, usuario);
        }

        return ehMembro;
	}

}
