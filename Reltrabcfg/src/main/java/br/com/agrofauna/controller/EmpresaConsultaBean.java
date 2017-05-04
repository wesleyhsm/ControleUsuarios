package br.com.agrofauna.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import br.com.agrofauna.filtro.EmpresaFiltro;
import br.com.agrofauna.model.Empresa;
import br.com.agrofauna.model.LogStatus;
import br.com.agrofauna.model.Sistema;
import br.com.agrofauna.model.UsuarioPermissao;
import br.com.agrofauna.service.EmpresaService;
import br.com.agrofauna.service.LogService;
import br.com.agrofauna.service.NegocioException;
import br.com.agrofauna.service.UsuarioPermissaoService;
import br.com.agrofauna.util.FacesUtil;

@Named
@ViewScoped
public class EmpresaConsultaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private EmpresaFiltro empresaFiltro;
	private LazyDataModel<Empresa> lazyEmpresa;
	private UsuarioPermissao usuarioPermissao;
	
	@Inject
	private EmpresaService empresaService;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private LogService logService;
	
	@Inject
	private UsuarioPermissaoService usuarioPermissaoService;
	
	@PostConstruct
	public void inicializar(){
		this.empresaFiltro = new EmpresaFiltro();
		this.lazyEmpresa = this.empresaService.listaTodasEmpresas(this.empresaFiltro);
		this.usuarioPermissao =  this.usuarioPermissaoService.pesquisarPermissao(this.loginBean, 7L);
	}	
	
	public void excluir(Empresa empresa){
		try{
			logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
								 empresa.getIdPessoa(), 
								 LogStatus.OK, 
								 "EmpresaConsultaBean",
								 "excluir", 
								 "empresa",
								 Sistema.RELTRABWEB_CFG,
								 "Empresa antes de excluir: "+ empresa.toString());
			
			this.empresaService.excluir(empresa);
			FacesUtil.addInfoMessage("Empresa excluida com sucesso.");
		}catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
	}
	
	public LazyDataModel<Empresa> getLazyEmpresa() {
		return lazyEmpresa;
	}
	public void setLazyEmpresa(LazyDataModel<Empresa> lazyEmpresa) {
		this.lazyEmpresa = lazyEmpresa;
	}
	
	public EmpresaFiltro getEmpresaFiltro() {
		return empresaFiltro;
	}
	public void setEmpresaFiltro(EmpresaFiltro empresaFiltro) {
		this.empresaFiltro = empresaFiltro;
	}

	public UsuarioPermissao getUsuarioPermissao() {
		return usuarioPermissao;
	}
	public void setUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		this.usuarioPermissao = usuarioPermissao;
	}	
}
