package com.hadrion.identidadeacesso.infraestrutura.servicos;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

import com.hadrion.identidadeacesso.comum.Afirmacao;
import com.hadrion.identidadeacesso.dominio.identidade.EncriptacaoService;

@Service
public class MD5EncriptacaoService extends Afirmacao 
	implements EncriptacaoService {

	@Override
	public String valorEncriptado(String valorEmTextoSimples) {
		this.assertArgumentoNaoVazio(
                valorEmTextoSimples,
                "Valor em texto simples para encriptar deve ser informado.");

        String valorEncriptado = null;

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.update(valorEmTextoSimples.getBytes("UTF-8"));

            BigInteger bigInt = new BigInteger(1, messageDigest.digest());

            valorEncriptado = bigInt.toString(16);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return valorEncriptado;
	}

}
