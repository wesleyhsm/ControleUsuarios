package br.com.agrofauna.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import br.com.agrofauna.filtro.LogFiltro;
import br.com.agrofauna.model.Log;
import br.com.agrofauna.model.UsuarioPermissao;
import br.com.agrofauna.service.LogService;
import br.com.agrofauna.service.UsuarioPermissaoService;

@Named
@ViewScoped
public class LogsConsultarBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private LogFiltro logFiltro;
	private LazyDataModel<Log> lazyLogs;
	private UsuarioPermissao usuarioPermissao;
	
	@Inject
	private LogService logService;
			
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private UsuarioPermissaoService usuarioPermissaoService;
	
	@PostConstruct
	public void inicializar(){
		this.logFiltro = new LogFiltro();
		this.lazyLogs = this.logService.listaTodasLogs(this.logFiltro);
		this.usuarioPermissao = this.usuarioPermissaoService.pesquisarPermissao(this.loginBean, 5L);
	}
	
	public String buscarNomeFuncionario(Log log){		
		return this.logService.buscarNomeFuncionario(log);
	}
	
	public LogFiltro getLogFiltro() {
		return logFiltro;
	}
	public void setLogFiltro(LogFiltro logFiltro) {
		this.logFiltro = logFiltro;
	}

	public LazyDataModel<Log> getLazyLogs() {
		return lazyLogs;
	}
	public void setLazyLogs(LazyDataModel<Log> lazyLogs) {
		this.lazyLogs = lazyLogs;
	}

	public UsuarioPermissao getUsuarioPermissao() {
		return usuarioPermissao;
	}
	public void setUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		this.usuarioPermissao = usuarioPermissao;
	}	
}
