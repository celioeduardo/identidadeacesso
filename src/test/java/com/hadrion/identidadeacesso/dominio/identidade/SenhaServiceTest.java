package com.hadrion.identidadeacesso.dominio.identidade;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.identidadeacesso.IdentidadeAcessoApplicationTest;

public class SenhaServiceTest extends IdentidadeAcessoApplicationTest{
	
	@Autowired
	private SenhaService senhaService;

	@Test
	public void testGenerateStrongPassword() throws Exception {
        String senha = senhaService.gerarSenhaForte();

        assertTrue(senhaService.ehForte(senha));
        assertFalse(senhaService.ehFraca(senha));
    }
	
	@Test
    public void testIsStrongPassword() throws Exception {
        final String senha = "Th1sShudBStrong.";
        assertTrue(senhaService.ehForte(senha));
        assertFalse(senhaService.ehMuitoForte(senha));
        assertFalse(senhaService.ehFraca(senha));
    }

	@Test
    public void testIsVeryStrongPassword() throws Exception {
        final String senha = "Th1sSh0uldBV3ryStrong!";
        assertTrue(senhaService.ehMuitoForte(senha));
        assertTrue(senhaService.ehForte(senha));
        assertFalse(senhaService.ehFraca(senha));
    }

	@Test
    public void testIsWeakPassword() throws Exception {
        final String senha = "Weakness";
        assertFalse(senhaService.ehMuitoForte(senha));
        assertFalse(senhaService.ehForte(senha));
        assertTrue(senhaService.ehFraca(senha));
    }
}
