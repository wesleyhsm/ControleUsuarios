package br.com.agrofauna.controller;

import java.io.Serializable;
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
import br.com.agrofauna.service.NegocioException;
import br.com.agrofauna.service.UsuarioPermissaoService;
import br.com.agrofauna.service.UsuariosService;
import br.com.agrofauna.util.FacesUtil;

@Named
@ViewScoped
public class UsuariosAdicionarBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;	
	private List<UsuarioPermissao> usuarioPermissoes;	
	private boolean nomeJanela;
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
	public void inicializar(){		
		limpar();
		this.usuarioPermissao = this.usuarioPermissaoService.pesquisarPermissao(this.loginBean, 9L);
		try{
			this.usuario.setIdUsuario( Long.parseLong( FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id") ) );
			this.usuario = this.usuariosService.buscarUsuarioPeloId(this.usuario);
			this.usuarioPermissoes = this.usuariosService.carregarPermissoesUsuario(this.usuario);			
			this.logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
									 this.usuario.getIdUsuario(), 
									 LogStatus.OK, 
									 "UsuariosAdicionarBean",
									 "editar", 
									 "usuario",
									 Sistema.RELTRABWEB_CFG,
									 "Usuario editar: "+ this.usuario.toString());
		}catch (Exception e){
			limpar2();
		}
	}
	
	public void salvar(){
		try{
			this.usuario = this.usuariosService.salvar(this.usuario, this.usuarioPermissoes);
			
			this.logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
					 this.usuario.getIdUsuario(), 
					 LogStatus.OK, 
					 "UsuariosAdicionarBean",
					 "salvar", 
					 "usuario",
					 Sistema.RELTRABWEB_CFG,
					 "Cadastro ou altero usuario: "+ this.usuario.toString());
			
			FacesUtil.addInfoMessage("Usuário ("+ this.usuario.getNmUsuario() +") salvo com sucesso.");
			limpar2();
			
		}catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro contatar administrador.");
		}
	}
		
	public void atualizarUsuarioPermissao(UsuarioPermissao usuarioPermissao){		
		for(int cont = 0; cont < this.usuarioPermissoes.size(); cont++){
			if(this.usuarioPermissoes.get(cont).getPermissao().getIdPermissao() == usuarioPermissao.getPermissao().getIdPermissao()){
				this.usuarioPermissoes.get(cont).setSnVisualizar(usuarioPermissao.getSnVisualizar());
				this.usuarioPermissoes.get(cont).setSnAdicionar(usuarioPermissao.getSnAdicionar());
				this.usuarioPermissoes.get(cont).setSnEditar(usuarioPermissao.getSnEditar());
				this.usuarioPermissoes.get(cont).setSnRemover(usuarioPermissao.getSnRemover());				
			}
		}
	}
	
	public void limpar(){
		this.usuario = new Usuario();
		this.nomeJanela = true;
	}

	public void limpar2(){
		this.usuario = new Usuario();
		this.usuarioPermissoes = this.usuariosService.carregarPermissoes();
		this.nomeJanela = false;
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
