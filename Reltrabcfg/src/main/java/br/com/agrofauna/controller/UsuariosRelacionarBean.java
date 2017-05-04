package br.com.agrofauna.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import br.com.agrofauna.filtro.EmpresaFiltro;
import br.com.agrofauna.filtro.FuncionarioFiltro;
import br.com.agrofauna.filtro.UsuariosFiltro;
import br.com.agrofauna.model.Empresa;
import br.com.agrofauna.model.Funcionario;
import br.com.agrofauna.model.LogStatus;
import br.com.agrofauna.model.Login;
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
public class UsuariosRelacionarBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private UsuariosFiltro usuariosFiltro;
	private LazyDataModel<Usuario> lazyUsuarios;	
	private EmpresaFiltro empresaFiltro;
	private LazyDataModel<Empresa> lazyEmpresa;
	private FuncionarioFiltro funcionarioFiltro;
	private LazyDataModel<Funcionario> lazyFuncionario;
	private Funcionario funcionario;
	private List<Empresa> empresas;
	private Usuario usuario;
	private Login login;
	private boolean nomeJanela;
	private UsuarioPermissao usuarioPermissao;	
	
	@Inject
	private UsuariosService usuariosService;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private LogService logService;

	@Inject
	private UsuarioPermissaoService usuarioPermissaoService;
	
	@PostConstruct
	public void inicializar(){
		limpar();
		this.lazyUsuarios = this.usuariosService.listaTodasUsuarios2(this.usuariosFiltro);
		this.lazyEmpresa = this.usuariosService.listaTodasEmpresas(this.empresaFiltro);
		this.lazyFuncionario = this.usuariosService.listaTodasFuncionarios(this.funcionarioFiltro);
		this.usuarioPermissao = this.usuarioPermissaoService.pesquisarPermissao(this.loginBean, 12L);
		
		try {
			this.usuario.setIdUsuario( Long.parseLong( FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")) );			
			this.funcionario = this.usuariosService.buscarFuncionarioLoginUsuario(this.usuario);			
			this.usuario = this.funcionario.getUsuario();			
			this.login = this.funcionario.getLogin();
			this.empresas = this.usuariosService.buscarFuncionarioEmpresas(this.usuario);			
			log("editar", "Usuario antes de editar: ");
		} catch (Exception e) {			
			this.nomeJanela = false;			
		}					
	}	
	
	public void limpar(){
		this.empresas = new ArrayList<Empresa>();
		this.usuario = new Usuario();
		this.funcionario = new Funcionario();
		this.usuariosFiltro = new UsuariosFiltro();		
		this.empresaFiltro = new EmpresaFiltro(); 
		this.funcionarioFiltro = new FuncionarioFiltro();
		this.login = new Login();
		this.nomeJanela = true;
	}
	
	public void log(String tipo, String msg){
		this.logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
				 this.funcionario.getIdPessoa(), 
				 LogStatus.OK, 
				 "UsuariosRelacionarBean",
				 tipo, 
				 "relacionarusuario",
				 Sistema.RELTRABWEB_CFG,
				 msg + this.usuario.toString());
		
		this.logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
				 this.funcionario.getIdPessoa(), 
				 LogStatus.OK, 
				 "UsuariosRelacionarBean",
				 tipo, 
				 "relacionarusuario",
				 Sistema.RELTRABWEB_CFG,
				 msg + this.login.toString());
		
		for(Empresa ep: empresas){
			this.logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
					 this.funcionario.getIdPessoa(), 
					 LogStatus.OK, 
					 "UsuariosRelacionarBean",
					 tipo, 
					 "relacionarusuario",
					 Sistema.RELTRABWEB_CFG,
					 msg + ep.toString());
		}
	}
	
	public void salvar(){
		try{
			this.usuariosService.salvarRelacionarUsuario(this.funcionario, this.empresas, this.usuario, this.login);
			FacesUtil.addInfoMessage("Funcionario relacionado ao usuario com sucesso.");
			log("salvar", "Usuario depois de salvar: ");			
			limpar();
		}catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao salvar, selecionar os campos corretamente.");
		} 
	}
	
	public void selecionaFuncionario(Funcionario funcionario){
		this.funcionario = funcionario;
		FacesUtil.addInfoMessage("Funcionario ("+ funcionario.getNmFuncionario() +") selecionado com sucesso.");
	}
	
	public void selecionaEmpresas(Empresa empresa){
		if(this.empresas.contains(empresa)){
			FacesUtil.addErrorMessage("Empresa CNPJ ("+ empresa.getNmCnpjCpf() +") já foi adicionada");
		}else{
			this.empresas.add(empresa);
			FacesUtil.addInfoMessage("Empresa CNPJ ("+ empresa.getNmCnpjCpf() +") selecionada com sucesso.");
		}	
	}
	
	public void removerEmpresas(Empresa empresa){
		this.empresas.remove(empresa);
		FacesUtil.addInfoMessage("Empresa CNPJ ("+ empresa.getNmCnpjCpf() +") removida com sucesso.");					
	}
	
	public void selecionaUsuario(Usuario usuario){
		this.usuario = usuario;
		FacesUtil.addInfoMessage("Usuario ("+ usuario.getNmUsuario() +") selecionado com sucesso.");
	}
	
	public UsuariosFiltro getUsuariosFiltro() {
		return usuariosFiltro;
	}
	public void setUsuariosFiltro(UsuariosFiltro usuariosFiltro) {
		this.usuariosFiltro = usuariosFiltro;
	}

	public LazyDataModel<Usuario> getLazyUsuarios() {
		return lazyUsuarios;
	}
	public void setLazyUsuarios(LazyDataModel<Usuario> lazyUsuarios) {
		this.lazyUsuarios = lazyUsuarios;
	}

	public EmpresaFiltro getEmpresaFiltro() {
		return empresaFiltro;
	}
	public void setEmpresaFiltro(EmpresaFiltro empresaFiltro) {
		this.empresaFiltro = empresaFiltro;
	}

	public LazyDataModel<Empresa> getLazyEmpresa() {
		return lazyEmpresa;
	}
	public void setLazyEmpresa(LazyDataModel<Empresa> lazyEmpresa) {
		this.lazyEmpresa = lazyEmpresa;
	}

	public FuncionarioFiltro getFuncionarioFiltro() {
		return funcionarioFiltro;
	}
	public void setFuncionarioFiltro(FuncionarioFiltro funcionarioFiltro) {
		this.funcionarioFiltro = funcionarioFiltro;
	}

	public LazyDataModel<Funcionario> getLazyFuncionario() {
		return lazyFuncionario;
	}
	public void setLazyFuncionario(LazyDataModel<Funcionario> lazyFuncionario) {
		this.lazyFuncionario = lazyFuncionario;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
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
