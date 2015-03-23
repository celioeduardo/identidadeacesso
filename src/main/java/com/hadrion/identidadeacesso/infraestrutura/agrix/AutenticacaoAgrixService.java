package com.hadrion.identidadeacesso.infraestrutura.agrix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hadrion.identidadeacesso.dominio.identidade.AutenticacaoService;
import com.hadrion.identidadeacesso.dominio.identidade.DescritorUsuario;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;

@Service
@Profile("agrix")
public class AutenticacaoAgrixService implements AutenticacaoService {
	
	@Autowired
	private AgrixConfiguracao configuracao;
	
	@Override
	public DescritorUsuario autenticar(HospedeId hospedeId, String username,
			String senha) {
		if (conectar(username, senha))
			return new DescritorUsuario(hospedeId, username, "");
		else
			return DescritorUsuario.intanciaDescritorNula();
	}
	
	private boolean conectar(String usuario, String senha){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("OracleDriver não encontrado",e);
		}
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(configuracao.getUrl(),usuario,senha);
		} catch (SQLException e) {
			return false;
		} finally {
			try {
				if (conn == null)
					return false;
				
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				return false;
			}
		}
		return true;
	}

}
