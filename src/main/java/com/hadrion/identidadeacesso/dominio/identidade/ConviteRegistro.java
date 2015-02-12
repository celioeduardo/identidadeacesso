package com.hadrion.identidadeacesso.dominio.identidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hadrion.identidadeacesso.comum.Afirmacao;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_CONVITE_REGISTRO")
@Table(name="CONVITE_REGISTRO")
public class ConviteRegistro extends Afirmacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="CONVITE_ID")
    private String conviteId;

    @Column(name="DESCRICAO")
    private String descricao;
	
    @Column(name="COMECANDO_EM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date comecandoEm;
    
    @Column(name="ATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ate;
    
    @Embedded
	private HospedeId hospedeId;
    
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
    
    public String descricao() {
        return this.descricao;
    }

    public String conviteId() {
        return this.conviteId;
    }

    public boolean estaDisponivel() {
        boolean estaDisponivel = false;
        if (this.comecandoEm() == null && this.ate() == null) {
            estaDisponivel = true;
        } else {
            long time = (new Date()).getTime();
            if (time >= this.comecandoEm().getTime() && time <= this.ate().getTime()) {
                estaDisponivel = true;
            }
        }
        return estaDisponivel;
    }

    public boolean identificadoPor(String identificadorConvite) {
        boolean estaIdentificadoPor = this.conviteId().equals(identificadorConvite);
        if (!estaIdentificadoPor && this.descricao() != null) {
            estaIdentificadoPor = this.descricao().equals(identificadorConvite);
        }
        return estaIdentificadoPor;
    }

    public ConviteRegistro duracaoPerpetua() {
        this.setComecandoEm(null);
        this.setAte(null);
        return this;
    }

    public ConviteRegistro redefinirComo() {
        this.setComecandoEm(null);
        this.setAte(null);
        return this;
    }

    public Date comecandoEm() {
        return this.comecandoEm;
    }

    public ConviteRegistro comecandoEm(Date data) {
        if (this.ate() != null) {
            throw new IllegalStateException("Não é possível começar depois da data até.");
        }

        this.setComecandoEm(data);

        // temporary if until() properly follows, but
        // prevents illegal state if until() doesn't follow
        this.setAte(new Date(data.getTime() + 86400000));

        return this;
    }

    public HospedeId hospedeId() {
        return this.hospedeId;
    }

    public DescritorConvite paraDescritor() {
        return
                new DescritorConvite(
                        this.hospedeId(),
                        this.conviteId(),
                        this.descricao(),
                        this.comecandoEm(),
                        this.ate());
    }

    public Date ate() {
        return this.ate;
    }

    public ConviteRegistro ate(Date data) {
        if (this.comecandoEm() == null) {
            throw new IllegalStateException("Não é possível definir data Até antes da data Começando Em.");
        }

        this.setAte(data);

        return this;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            ConviteRegistro typedObject = (ConviteRegistro) anObject;
            equalObjects =
                this.hospedeId().equals(typedObject.hospedeId()) &&
                this.conviteId().equals(typedObject.conviteId());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (6325 * 233)
            + this.hospedeId().hashCode()
            + this.conviteId().hashCode();

        return hashCodeValue;
    }


    @Override
    public String toString() {
        return "ConviteRegistro ["
                + "hospedeId=" + hospedeId
                + ", descricao=" + descricao
                + ", conviteId=" + conviteId
                + ", comecandoEm=" + comecandoEm
                + ", ate=" + ate + "]";
    }

    protected ConviteRegistro(
            HospedeId aHospedeId,
            String anInvitationId,
            String aDescricao) {

        this();

        this.setDescricao(aDescricao);
        this.setInvitationId(anInvitationId);
        this.setHospedeId(aHospedeId);

        this.assertDatasConviteValidas();
    }

    protected ConviteRegistro() {
        super();
    }

    protected void assertDatasConviteValidas() {
        // either both dates must be null, or both dates must be set
        if (this.comecandoEm() == null && this.ate() == null) {
            ; // valid
        } else if (this.comecandoEm() == null || this.ate() == null &&
                   this.comecandoEm() != this.ate()) {
            throw new IllegalStateException("Este é um convite inválido de duração indeterminada.");
        } else if (this.comecandoEm().after(this.ate())) {
            throw new IllegalStateException("A data e hora de começo precisa ser anterior a data e hora até.");
        }
    }

    protected void setDescricao(String descricao) {
        this.assertArgumentoNaoVazio(descricao, "A descrição do convite é obrigatória.");
        this.assertTamanhoArgumento(descricao, 1, 100, "A descrição do convite precisa ter 100 caracteres ou menos.");

        this.descricao = descricao;
    }

    protected void setInvitationId(String conviteId) {
        this.assertArgumentoNaoVazio(conviteId, "O conviteId é obrigatório.");
        this.assertTamanhoArgumento(conviteId, 1, 36, "O convite id precisa ter 36 caracteres ou menos.");

        this.conviteId = conviteId;
    }

    protected void setComecandoEm(Date aStartingOn) {
        this.comecandoEm = aStartingOn;
    }

    protected void setHospedeId(HospedeId hospedeId) {
        this.assertArgumentoNaoNulo(hospedeId, "O hospedeId é obrigatório.");

        this.hospedeId = hospedeId;
    }

    protected void setAte(Date ate) {
        this.ate = ate;
    }

	public ConviteRegistro perpetuo() {
		this.setComecandoEm(null);
        this.setAte(null);
        return this;
	}
	
}
