package br.com.agrofauna.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.agrofauna.model.LogStatus;
import br.com.agrofauna.model.Sistema;
import br.com.agrofauna.model.Usuario;
import br.com.agrofauna.model.UsuarioPermissao;
import br.com.agrofauna.service.LogService;
import br.com.agrofauna.service.UsuarioPermissaoService;
import br.com.agrofauna.service.UsuariosService;
import br.com.agrofauna.util.FacesUtil;

@Named
@ViewScoped
public class UsuariosVisualizarBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private List<UsuarioPermissao> usuarioPermissoes;
	private UsuarioPermissao usuarioPermissao;
	
	@Inject
	private UsuariosService usuariosService;
	
	@Inject
	private LogService logService;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private UsuarioPermissaoService usuarioPermissaoService;
	
	@PostConstruct
	public void inicializar() {
		try{
			limpar();
			this.usuarioPermissao = this.usuarioPermissaoService.pesquisarPermissao(this.loginBean, 11L);
			this.usuario.setIdUsuario( Long.parseLong( FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id") ) );
			this.usuario = this.usuariosService.buscarUsuarioPeloId(this.usuario);
			this.usuarioPermissoes = this.usuariosService.carregarPermissoesUsuario(this.usuario);			
			
			this.logService.salvarLog(loginBean.getFuncionario().getIdPessoa(), 
									 this.usuario.getIdUsuario(), 
									 LogStatus.OK, 
									 "UsuariosVisializarBean",
									 "visualizar", 
									 "usuario",
									 Sistema.RELTRABWEB_CFG,
									 "Usuario visualizar: "+ this.usuario.toString());
			
		}catch (Exception e) {
			FacesUtil.addErrorMessage("Erro ao visualizar usuario");
		}
	}
	
	public void limpar() {
		this.usuario = new Usuario();
		this.usuarioPermissoes = new ArrayList<UsuarioPermissao>();
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<UsuarioPermissao> getUsuarioPermissoes() {
		return usuarioPermissoes;
	}
	public void setUsuarioPermissoes(List<UsuarioPermissao> usuarioPermissoes) {
		this.usuarioPermissoes = usuarioPermissoes;
	}

	public UsuarioPermissao getUsuarioPermissao() {
		return usuarioPermissao;
	}
	public void setUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		this.usuarioPermissao = usuarioPermissao;
	}	
}
