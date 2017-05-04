package br.com.agrofauna.validation;

import java.io.Serializable;

public class RetiraCaracteres implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static String removerTodosCaracterEspecial(String campo){			
		campo = campo.replaceAll("\\.", "");
		campo = campo.replaceAll("\\-", "");
		campo = campo.replaceAll("/", "");		
		return campo;
	}	
}
