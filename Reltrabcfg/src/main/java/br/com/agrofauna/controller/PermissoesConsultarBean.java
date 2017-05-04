package br.com.agrofauna.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import br.com.agrofauna.filtro.PermissoesFiltro;
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
public class PermissoesConsultarBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private PermissoesFiltro permissoesFiltro;
	private List<PermissaoSetor> permissaoSetores;
	private LazyDataModel<Permissao> lazyPermissoes;
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
		this.permissoesFiltro = new PermissoesFiltro();
		this.permissaoSetores = Arrays.asList(PermissaoSetor.values());	
		this.lazyPermissoes = this.permissaoService.listaTodasPermissoes(this.permissoesFiltro);
		this.usuarioPermissao = this.usuarioPermissaoService.pesquisarPermissao(this.loginBean, 1L);		
	}	
	
	public void excluir(Permissao permissao){
		try{
			logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
								 permissao.getIdPermissao(), 
								 LogStatus.OK, 
								 "PermissoesConsultarBean",
								 "excluir", 
								 "permissao",
								 Sistema.RELTRABWEB_CFG,
								 "Permissão antes de excluir: "+ permissao.toString());
			
			this.permissaoService.excluir(permissao);
			FacesUtil.addInfoMessage("Permissão excluida com sucesso.");
		}catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public List<PermissaoSetor> getPermissaoSetores() {
		return permissaoSetores;
	}
	public void setPermissaoSetores(List<PermissaoSetor> permissaoSetores) {
		this.permissaoSetores = permissaoSetores;
	}

	public PermissoesFiltro getPermissoesFiltro() {
		return permissoesFiltro;
	}
	public void setPermissoesFiltro(PermissoesFiltro permissoesFiltro) {
		this.permissoesFiltro = permissoesFiltro;
	}

	public LazyDataModel<Permissao> getLazyPermissoes() {
		return lazyPermissoes;
	}
	public void setLazyPermissoes(LazyDataModel<Permissao> lazyPermissoes) {
		this.lazyPermissoes = lazyPermissoes;
	}

	public UsuarioPermissao getUsuarioPermissao() {
		return usuarioPermissao;
	}
	public void setUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		this.usuarioPermissao = usuarioPermissao;
	}	
}
