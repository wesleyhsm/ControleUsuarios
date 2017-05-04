package br.com.agrofauna.service;

import java.io.Serializable;

public class NegocioException extends Exception implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public NegocioException(String message) {
		super(message);
	}
	
}
