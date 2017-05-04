package br.com.agrofauna.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.agrofauna.model.Usuario;
import br.com.agrofauna.model.UsuarioEmpresa;
import br.com.agrofauna.repository.UsuarioEmpresaRepository;

public class UsuarioEmpresaService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioEmpresaRepository usuarioEmpresaRepository;
	
	@Transactional
	public void salvar(UsuarioEmpresa usuarioEmpresa) throws NegocioException {
				
		if(this.usuarioEmpresaRepository.verificaExisteUsuarioEmpresa(usuarioEmpresa) != null){
			throw new NegocioException("Erro usuario esta em uso.");
		}
		
		this.usuarioEmpresaRepository.salvar(usuarioEmpresa);	
	}
	
	public UsuarioEmpresa verificaExisteUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa){
		return this.usuarioEmpresaRepository.verificaExisteUsuarioEmpresa(usuarioEmpresa);
	}
		
	public void limparUsuarioEmpresa(Usuario usuario) {
		this.usuarioEmpresaRepository.limparUsuarioEmpresa(usuario);		
	}
}
