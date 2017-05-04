package br.com.agrofauna.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.com.agrofauna.model.Usuario;
import br.com.agrofauna.model.UsuarioEmpresa;

public class UsuarioEmpresaRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;
		
	public UsuarioEmpresa salvar(UsuarioEmpresa usuarioEmpresa) {		
		return this.manager.merge(usuarioEmpresa);
	}

	public UsuarioEmpresa verificaExisteUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
		try{
			return (UsuarioEmpresa) this.manager.createNamedQuery("UsuarioEmpresa.existe")
								 .setParameter("idUsuario", usuarioEmpresa.getUsuario().getIdUsuario())
								 .setParameter("idPessoa", usuarioEmpresa.getEmpresa().getIdPessoa())
								 .getSingleResult();
		}catch (NoResultException nre){
			return null;
		}	
	}

	public void limparUsuarioEmpresa(Usuario usuario) {
		 this.manager.createNamedQuery("UsuarioEmpresa.limparUsuarioEmpresa")
		 			 .setParameter("idUsuario", usuario.getIdUsuario())		 
		 			 .executeUpdate();	
	}	
}
