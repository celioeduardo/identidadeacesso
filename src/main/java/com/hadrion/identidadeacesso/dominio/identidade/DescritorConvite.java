package com.hadrion.identidadeacesso.dominio.identidade;

import java.util.Date;

import com.hadrion.identidadeacesso.comum.Afirmacao;

public final class DescritorConvite extends Afirmacao {

    private String descricao;
    private String conviteId;
    private Date comecandoEm;
    private HospedeId hospedeId;
    private Date ate;

    public DescritorConvite(
            HospedeId hospedeId,
            String conviteId,
            String descricao,
            Date comecandoEm ,
            Date ate) {

        super();

        this.setDescricao(descricao);
        this.setConviteId(conviteId);
        this.setComecandoEm (comecandoEm );
        this.setHospedeId(hospedeId);
        this.setAte(ate);
    }

    public DescritorConvite(DescritorConvite anInvitationDescriptor) {
        this(anInvitationDescriptor.hospedeId(),
             anInvitationDescriptor.conviteId(),
             anInvitationDescriptor.descricao(),
             anInvitationDescriptor.comecandoEm(),
             anInvitationDescriptor.ate());
    }

    public String descricao() {
        return this.descricao;
    }

    public String conviteId() {
        return this.conviteId;
    }

    public boolean isOpenEnded() {
        return this.comecandoEm() == null && this.ate() == null;
    }

    public Date comecandoEm() {
        return this.comecandoEm;
    }

    public HospedeId hospedeId() {
        return this.hospedeId;
    }

    public Date ate() {
        return this.ate;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            DescritorConvite typedObject = (DescritorConvite) anObject;
            equalObjects =
                this.hospedeId().equals(typedObject.hospedeId()) &&
                this.conviteId().equals(typedObject.conviteId()) &&
                this.descricao().equals(typedObject.descricao()) &&
                ((this.comecandoEm() == null && typedObject.comecandoEm() == null) ||
                 (this.comecandoEm() != null && this.comecandoEm().equals(typedObject.comecandoEm()))) &&
                ((this.ate() == null && typedObject.ate() == null) ||
                 (this.ate() != null && this.ate().equals(typedObject.ate())));
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (23279 * 199)
            + this.hospedeId().hashCode()
            + this.conviteId().hashCode()
            + this.descricao().hashCode()
            + (this.comecandoEm() == null ? 0:this.comecandoEm().hashCode())
            + (this.ate() == null ? 0:this.ate().hashCode());

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "DescritorConvite [hospedeId=" + hospedeId
                + ", conviteId=" + conviteId
                + ", descricao=" + descricao
                + ", comecandoEm=" + comecandoEm + ", ate=" + ate + "]";
    }

    protected DescritorConvite() {
        super();
    }

    private void setDescricao(String descricao) {
        this.assertArgumentoNaoNulo(descricao, "A descrição do convite é obrigatória.");

        this.descricao = descricao;
    }

    private void setConviteId(String conviteId) {
        this.assertArgumentoNaoNulo(conviteId, "O conviteId é obrigatório.");

        this.conviteId = conviteId;
    }

    private void setComecandoEm (Date aComecandoEm ) {
        this.comecandoEm = aComecandoEm ;
    }

    private void setHospedeId(HospedeId hospedeId) {
        this.assertArgumentoNaoNulo(hospedeId, "O hospedeId é obrigatório.");

        this.hospedeId = hospedeId;
    }

    private void setAte(Date valor) {
        this.ate = valor;
    }
}
