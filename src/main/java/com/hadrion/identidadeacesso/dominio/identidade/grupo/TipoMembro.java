package com.hadrion.identidadeacesso.dominio.identidade.grupo;

public enum TipoMembro {
	
	Grupo{
		public boolean ehGrupo(){
			return true;
		}
	},
	
	Usuario{
		public boolean ehUsuario(){
			return true;
		}
	};
	
	public boolean ehGrupo() {
        return false;
    }

    public boolean ehUsuario() {
        return false;
    }
}
