package br.com.agrofauna.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import br.com.agrofauna.filtro.UsuariosFiltro;
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
public class UsuariosConsultaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UsuariosFiltro usuariosFiltro;
	private LazyDataModel<Usuario> lazyUsuarios;
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
		this.usuariosFiltro = new UsuariosFiltro();
		this.lazyUsuarios = this.usuariosService.listaTodasUsuarios(this.usuariosFiltro);
		this.usuarioPermissao = this.usuarioPermissaoService.pesquisarPermissao(this.loginBean, 10L);
	}
	
	public void excluir(Usuario usuario){		
		try{
			logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
								 usuario.getIdUsuario(), 
								 LogStatus.OK, 
								 "UsuariosConsultarBean",
								 "excluir", 
								 "usuario",
								 Sistema.RELTRABWEB_CFG,
								 "usuario antes de excluir: "+ usuario.toString());
			
			this.usuariosService.excluirUsuario(usuario);			
			FacesUtil.addInfoMessage("Usuario ("+ usuario.getNmUsuario() +") excluido com sucesso.");
		}catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public String buscarFuncionario(Usuario usuario){
		return this.usuariosService.buscarFuncionario(usuario);
	}
	
	public LazyDataModel<Usuario> getLazyUsuarios() {
		return lazyUsuarios;
	}
	public void setLazyUsuarios(LazyDataModel<Usuario> lazyUsuarios) {
		this.lazyUsuarios = lazyUsuarios;
	}

	public UsuariosFiltro getUsuariosFiltro() {
		return usuariosFiltro;
	}
	public void setUsuariosFiltro(UsuariosFiltro usuariosFiltro) {
		this.usuariosFiltro = usuariosFiltro;
	}

	public UsuarioPermissao getUsuarioPermissao() {
		return usuarioPermissao;
	}
	public void setUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		this.usuarioPermissao = usuarioPermissao;
	}	
}
