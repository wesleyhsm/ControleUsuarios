package br.com.agrofauna.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.agrofauna.model.Estado;

public class EstadoRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="mydb")
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<Estado> buscarTodosEstados(){
		return  this.manager.createNamedQuery("Estado.findAll").getResultList();
	}

	public Estado buscarEstadosSigla(String sigla) {		
		return (Estado) this.manager.createNamedQuery("Estado.sigla")
				.setParameter("nmSigla", sigla)
				.getSingleResult();
	}
}
