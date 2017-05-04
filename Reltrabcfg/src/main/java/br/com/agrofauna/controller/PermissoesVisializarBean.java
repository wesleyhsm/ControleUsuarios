package br.com.agrofauna.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.agrofauna.model.LogStatus;
import br.com.agrofauna.model.Permissao;
import br.com.agrofauna.model.Sistema;
import br.com.agrofauna.model.UsuarioPermissao;
import br.com.agrofauna.service.LogService;
import br.com.agrofauna.service.PermissaoService;
import br.com.agrofauna.service.UsuarioPermissaoService;
import br.com.agrofauna.util.FacesUtil;

@Named
@ViewScoped
public class PermissoesVisializarBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Permissao permissao;
	private UsuarioPermissao usuarioPermissao;
	
	@Inject
	private PermissaoService permissaoService;
	
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
			this.permissao.setIdPermissao( Long.parseLong( FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));
			this.permissao = this.permissaoService.buscarPermissaoPeloId(this.permissao);
			this.usuarioPermissao = this.usuarioPermissaoService.pesquisarPermissao(this.loginBean, 3L);
			
			this.logService.salvarLog(loginBean.getFuncionario().getIdPessoa(), 
									 this.permissao.getIdPermissao(), 
									 LogStatus.OK, 
									 "PermissoesVisializarBean",
									 "visualizar", 
									 "permissao",
									 Sistema.RELTRABWEB_CFG,
									 "Permissão visualizar: "+ this.permissao.toString());
			
		}catch (Exception e) {
			FacesUtil.addErrorMessage("Erro ao visualizar permissão");
		}
	}
	
	public void limpar() {
		this.permissao = new Permissao();
	}

	public Permissao getPermissao() {
		return permissao;
	}
	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public UsuarioPermissao getUsuarioPermissao() {
		return usuarioPermissao;
	}
	public void setUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		this.usuarioPermissao = usuarioPermissao;
	}	
}
