package br.com.agrofauna.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.agrofauna.model.Funcionario;
import br.com.agrofauna.model.Login;
import br.com.agrofauna.repository.LoginRepository;

public class LoginService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private LoginRepository loginRepository;
		
	public Login salvar(Login login) {		 		
		return this.loginRepository.salvar(login); 
	}
	
	public Funcionario buscarLogin(Login login) throws NegocioException {
		Funcionario funcionario = this.loginRepository.buscarLogin(login);
		return funcionario;
	}
}
