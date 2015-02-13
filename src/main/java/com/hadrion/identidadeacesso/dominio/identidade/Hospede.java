package com.hadrion.identidadeacesso.dominio.identidade;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.hadrion.identidadeacesso.comum.Afirmacao;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_HOSPEDE")
@Table(name="HOSPEDE")
public class Hospede extends Afirmacao{
	
	@Embedded
	private HospedeId hospedeId;
	
	@Column(name="NOME")
	private String nome;

	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="ATIVO")
	private boolean ativo;
	
	@OneToMany(orphanRemoval=true,cascade=CascadeType.ALL)
	@JoinTable(name="CONVITES_REGISTRO",
	    joinColumns=@JoinColumn(name="ID_HOSPEDE"),
	    inverseJoinColumns=@JoinColumn(name="ID_CONVITE_REGISTRO"))
	private Set<ConviteRegistro> convitesRegistro;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;

	public Hospede(HospedeId hospedeId, String nome, String descricao,
			boolean ativo) {
		super();
		this.hospedeId = hospedeId;
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
	}
	
	@SuppressWarnings("unused")
	private Hospede(){}

	public HospedeId hospedeId() {
		return hospedeId;
	}

	public String nome() {
		return nome;
	}

	public String descricao() {
		return descricao;
	}
	
	public boolean estaAtivo(){
		return ativo;
	}

	public void desativar() {
		ativo = false;
	}
	
	public ConviteRegistro oferecerConviteRegistro(
			String descricao) {
		this.assertEstadoVerdadeiro(this.estaAtivo(), "Hóspede não está ativo.");

		this.assertEstadoFalso(
				this.registroEstaDisponivelPara(descricao),
				"Invitation already exists.");

		ConviteRegistro convite = new ConviteRegistro(
				this.hospedeId(), UUID.randomUUID().toString().toUpperCase(),
				descricao);

		boolean adicionado = this.getConvitesRegistro().add(convite);

		this.assertEstadoVerdadeiro(adicionado, "O convite deveria ter sido adicionado.");

		return convite;
	}
	
	public boolean registroEstaDisponivelPara (String identificadorConvite) {
		this.assertEstadoVerdadeiro(this.estaAtivo(), "Hóspede não está ativo.");
		
		ConviteRegistro convite =
            this.convite(identificadorConvite);

        return convite == null ? false : convite.estaDisponivel();
    }
	
	protected ConviteRegistro convite(String identificadorConvite) {
		for (ConviteRegistro convite : this.getConvitesRegistro()) {
			if (convite.identificadoPor(identificadorConvite)) {
				return convite;
			}
		}

		return null;
	}

	protected Set<ConviteRegistro> getConvitesRegistro() {
		if (convitesRegistro == null)
			convitesRegistro = new HashSet<ConviteRegistro>();
		return convitesRegistro;
	}

	public Usuario registrarUsuario(
			String conviteId, 
			String username,
			String senha, 
			Ativacao ativacao, 
			Pessoa pessoa) {
		
		this.assertEstadoVerdadeiro(this.estaAtivo(), "Hóspede não está ativo.");

        Usuario usuario = null;

        if (this.registroEstaDisponivelPara(conviteId)) {

            //garante o mesmo hóspede
        	pessoa.setHospedeId(this.hospedeId());

            usuario = new Usuario(
                    this.hospedeId(),
                    username,
                    senha,
                    ativacao,
                    pessoa);
        }

        return usuario;
	}

	public ConviteRegistro redefinirConviteRegistroComo(String identificadorConvite) {
		this.assertEstadoVerdadeiro(this.estaAtivo(), "Hóspede não está ativo.");

		ConviteRegistro convite =
            this.convite(identificadorConvite);

        if (convite != null) {
            convite.redefinirComo().perpetuo();
        }

        return convite;
    }

	public Collection<DescritorConvite> todosConvitesRegistroDisponiveis() {
		this.assertEstadoVerdadeiro(this.estaAtivo(), "Hóspede não está ativo.");
		return todosConvitesRegistroQue(true);
	}
	
	protected Collection<DescritorConvite> todosConvitesRegistroQue(boolean estaDisponivel) {
        Set<DescritorConvite> todosConvites = new HashSet<>();

        for (ConviteRegistro convite : this.getConvitesRegistro()) {
            if (convite.estaDisponivel() == estaDisponivel) {
                todosConvites.add(convite.paraDescritor());
            }
        }

        return Collections.unmodifiableSet(todosConvites);
    }
	
	public void retirarConvite(String identificadorConvite) {
        ConviteRegistro convite =
            this.convite(identificadorConvite);

        if (convite != null) {
            this.getConvitesRegistro().remove(convite);
        }
    }
	
}
