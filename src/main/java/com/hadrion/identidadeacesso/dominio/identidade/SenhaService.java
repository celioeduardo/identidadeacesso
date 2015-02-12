package com.hadrion.identidadeacesso.dominio.identidade;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.hadrion.identidadeacesso.comum.Afirmacao;

@Service
public final class SenhaService extends Afirmacao {

    private static final String DIGITOS = "0123456789";
    private static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LIMIAR_FORTE = 20;
    private static final String SIMBOLOS = "\"`!?$?%^&*()_-+={[}]:;@'~#|\\<,>.?/";
    private static final int LIMIAR_MUITO_FORTE = 40;

    public SenhaService() {
        super();
    }

    public String gerarSenhaForte() {
        String senhaGerada = null;

        StringBuffer senha = new StringBuffer();

        Random random = new Random();

        boolean ehForte = false;

        int index = 0;

        while (!ehForte) {

            int opt = random.nextInt(4);

            switch (opt) {
            case 0:
                index = random.nextInt(LETRAS.length());
                senha.append(LETRAS.substring(index, index+1));
                break;
            case 1:
                index = random.nextInt(LETRAS.length());
                senha.append(LETRAS.substring(index, index+1).toLowerCase());
                break;
            case 2:
                index = random.nextInt(DIGITOS.length());
                senha.append(DIGITOS.substring(index, index+1));
                break;
            case 3:
                index = random.nextInt(SIMBOLOS.length());
                senha.append(SIMBOLOS.substring(index, index+1));
                break;
            }

            senhaGerada = senha.toString();

            if (senhaGerada.length() >= 7) {
                ehForte = this.ehForte(senhaGerada);
            }
        }

        return senhaGerada;
    }

    public boolean ehForte(String senhaTextoSimples) {
        return this.calcularForcaDaSenha(senhaTextoSimples) >= LIMIAR_FORTE;
    }

    public boolean ehMuitoForte(String senhaTextoSimples) {
        return this.calcularForcaDaSenha(senhaTextoSimples) >= LIMIAR_MUITO_FORTE;
    }

    public boolean ehFraca(String senhaTextoSimples) {
        return this.calcularForcaDaSenha(senhaTextoSimples) < LIMIAR_FORTE;
    }

    private int calcularForcaDaSenha(String senhaTextoSimples) {
        this.assertArgumentoNaoNulo(senhaTextoSimples, "Força da senha não pode ser testado em nulo.");

        int strength = 0;

        int length = senhaTextoSimples.length();

        if (length > 7) {
            strength += 10;
            // bonus: one point each additional
            strength += (length - 7);
        }

        int digitCount = 0;
        int letterCount = 0;
        int lowerCount = 0;
        int upperCount = 0;
        int symbolCount = 0;

        for (int idx = 0; idx < length; ++idx) {

            char ch = senhaTextoSimples.charAt(idx);

            if (Character.isLetter(ch)) {
                ++letterCount;
                if (Character.isUpperCase(ch)) {
                    ++upperCount;
                } else {
                    ++lowerCount;
                }
            } else if (Character.isDigit(ch)) {
                ++digitCount;
            } else {
                ++symbolCount;
            }
        }

        strength += (upperCount + lowerCount + symbolCount);

        // bonus: letters and digits
        if (letterCount >= 2 && digitCount >= 2) {
            strength += (letterCount + digitCount);
        }

        return strength;
    }
}
