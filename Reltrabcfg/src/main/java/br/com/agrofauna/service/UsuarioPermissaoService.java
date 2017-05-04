package br.com.agrofauna.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.agrofauna.controller.LoginBean;
import br.com.agrofauna.model.Usuario;
import br.com.agrofauna.model.UsuarioPermissao;
import br.com.agrofauna.repository.UsuarioPermissaoRepository;

public class UsuarioPermissaoService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioPermissaoRepository usuarioPermissaoRepository;
	
	public UsuarioPermissao salvar(UsuarioPermissao usuarioPermissao) {
		return this.usuarioPermissaoRepository.salvar(usuarioPermissao);
		
	} 
	
	public List<UsuarioPermissao> carregarPermissoesUsuario(Usuario usuario){
		return this.usuarioPermissaoRepository.carregarPermissoesUsuario(usuario);
	}
	
	public void excluir(Usuario usuario){
		this.usuarioPermissaoRepository.excluir(usuario);
	}
	
	public UsuarioPermissao pesquisarPermissao(LoginBean loginBean, Long idPermissao){
		return this.usuarioPermissaoRepository.pesquisarPermissao(loginBean, idPermissao);
	}
}
