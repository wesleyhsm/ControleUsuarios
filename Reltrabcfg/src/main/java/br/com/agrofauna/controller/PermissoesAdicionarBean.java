package br.com.agrofauna.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.agrofauna.model.LogStatus;
import br.com.agrofauna.model.Permissao;
import br.com.agrofauna.model.PermissaoSetor;
import br.com.agrofauna.model.Sistema;
import br.com.agrofauna.model.UsuarioPermissao;
import br.com.agrofauna.service.LogService;
import br.com.agrofauna.service.NegocioException;
import br.com.agrofauna.service.PermissaoService;
import br.com.agrofauna.service.UsuarioPermissaoService;
import br.com.agrofauna.util.FacesUtil;

@Named
@ViewScoped
public class PermissoesAdicionarBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Permissao permissao;
	private List<PermissaoSetor> permissaoSetores;
	private boolean nomeJanela;
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
	public void inicializar(){
		this.usuarioPermissao = this.usuarioPermissaoService.pesquisarPermissao(this.loginBean, 4L);
		limpar();
		this.permissaoSetores = Arrays.asList(PermissaoSetor.values());
		
		try {			
			this.permissao.setIdPermissao( Long.parseLong( FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));		
			this.permissao = permissaoService.buscarPermissaoPeloId(this.permissao);			
			this.nomeJanela = true;
			
			this.logService.salvarLog(loginBean.getFuncionario().getIdPessoa(), 
								 this.permissao.getIdPermissao(), 
								 LogStatus.OK, 
								 "PermissoesAdicionarBean",
								 "editar", 
								 "permissao",
								 Sistema.RELTRABWEB_CFG,
								 "Permissão antes de editar: "+ this.permissao.toString());
			
		} catch (Exception e) {
			this.nomeJanela = false;
		}
	}
	
	public void salvar(){
		try{
			this.permissao = permissaoService.salvar(this.permissao);
			
			this.logService.salvarLog(loginBean.getFuncionario().getIdPessoa(), 
								 this.permissao.getIdPermissao(), 
								 LogStatus.OK, 
								 "PermissoesAdicionarBean",
								 "salvar", 
								 "permissao",
								 Sistema.RELTRABWEB_CFG,
								 "Cadastro ou altero permissao: "+ this.permissao.toString());
			
			FacesUtil.addInfoMessage("Permissão ("+this.permissao.getIdPermissao()+") salvo com sucesso.");			
			limpar();
			
		}catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
			
		}catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao salvar permissão contatar administrador.");
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

	public List<PermissaoSetor> getPermissaoSetores() {
		return permissaoSetores;
	}
	public void setPermissaoSetores(List<PermissaoSetor> permissaoSetores) {
		this.permissaoSetores = permissaoSetores;
	}

	public boolean isNomeJanela() {
		return nomeJanela;
	}
	public void setNomeJanela(boolean nomeJanela) {
		this.nomeJanela = nomeJanela;
	}
	
	public UsuarioPermissao getUsuarioPermissao() {
		return usuarioPermissao;
	}
	public void setUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		this.usuarioPermissao = usuarioPermissao;
	}	
}
