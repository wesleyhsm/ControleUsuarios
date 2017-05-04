package br.com.agrofauna.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.agrofauna.model.Estado;
import br.com.agrofauna.repository.EstadoRepository;

public class EstadoService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EstadoRepository estadoRepository;
	
	public List<Estado> buscarTodosEstados(){
		return this.estadoRepository.buscarTodosEstados();
	}
	
	public Estado buscarEstadosSigla(String sigla){
		return this.estadoRepository.buscarEstadosSigla(sigla);
	}
}
