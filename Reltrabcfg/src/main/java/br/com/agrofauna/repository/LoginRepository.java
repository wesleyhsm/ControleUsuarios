package br.com.agrofauna.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.agrofauna.model.Funcionario;
import br.com.agrofauna.model.Login;

import br.com.agrofauna.service.NegocioException;

public class LoginRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;

	public Login salvar(Login login) {		 		
		return this.manager.merge(login); 
	}
	
	public Funcionario buscarLogin(Login login) throws NegocioException {
		try{
			Query query = this.manager.createNamedQuery("Login.funcionario")
								 .setParameter("login", login.getNmLogin())
								 .setParameter("senha", login.getNmSenha());		
			return (Funcionario) query.getSingleResult();
		}catch (NoResultException nre){
			throw new NegocioException("Usuário inválido ou desativado.");
		}	
	}
}
