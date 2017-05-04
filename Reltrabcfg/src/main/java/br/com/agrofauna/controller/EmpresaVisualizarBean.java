package br.com.agrofauna.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.agrofauna.model.Email;
import br.com.agrofauna.model.EmailTipo;
import br.com.agrofauna.model.Empresa;
import br.com.agrofauna.model.Endereco;
import br.com.agrofauna.model.EnderecoTipo;
import br.com.agrofauna.model.Estado;
import br.com.agrofauna.model.LogStatus;
import br.com.agrofauna.model.NfeConfiguracao;
import br.com.agrofauna.model.Sistema;
import br.com.agrofauna.model.Telefone;
import br.com.agrofauna.model.TelefoneTipo;
import br.com.agrofauna.model.UsuarioPermissao;
import br.com.agrofauna.service.EmpresaService;
import br.com.agrofauna.service.LogService;
import br.com.agrofauna.service.UsuarioPermissaoService;
import br.com.agrofauna.util.FacesUtil;

@Named
@ViewScoped
public class EmpresaVisualizarBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Empresa empresa;	
	private Endereco endereco;
	private Estado estado;
	private Telefone telefone;
	private Email email;
	private NfeConfiguracao nfeConfiguracao;
	private List<EnderecoTipo> EnderecoTipos;
	private List<Estado> estados;
	private List<Endereco> enderecos;
	private List<TelefoneTipo> telefoneTipos;
	private List<Telefone> telefones;
	private List<Email> emails;	
	private List<EmailTipo> emailTipos; 
	private List<NfeConfiguracao> nfeConfiguracaes;
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
	public void inicializar() {
		try{
			limpar();
			limparEmpresa();		
			this.empresa.setIdPessoa( Long.parseLong( FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id") ) );
			this.empresa = this.empresaService.buscarEmpresaPeloId(this.empresa);			
			this.estados = this.empresaService.buscarTodosEstados();
			this.EnderecoTipos = Arrays.asList(EnderecoTipo.values());			
			this.telefoneTipos = Arrays.asList(TelefoneTipo.values());			
			this.emailTipos = Arrays.asList(EmailTipo.values());
			limparTelefone();
			limparEmail();
			limparNfeConfiguracao();			
			this.enderecos = this.empresaService.buscarEnderecoPeloId(this.empresa);			
			this.telefones =  this.empresaService.buscarTelefonePeloId(this.empresa);			
			this.emails = this.empresaService.buscarEmailPeloId(this.empresa);			
			this.nfeConfiguracaes = this.empresaService.buscarNfeConfiguracaoPeloId(this.empresa);
			this.usuarioPermissao =  this.usuarioPermissaoService.pesquisarPermissao(this.loginBean, 8L);
			
			this.logService.salvarLog(loginBean.getFuncionario().getIdPessoa(), 
					 this.empresa.getIdPessoa(), 
					 LogStatus.OK, 
					 "EmpresaVisualizarBean",
					 "visualizar", 
					 "empresa",
					 Sistema.RELTRABWEB_CFG,
					 "Empresa visualizar: "+ this.empresa.toString());			
			
		}catch (Exception e) {
			FacesUtil.addErrorMessage("Erro ao visualizar empresa");
		}
	}
	
	public void limpar(){		
		this.endereco = new Endereco();
		this.estado = new Estado();
	}
	
	public void limparEmpresa(){		
		this.empresa = new Empresa();		
	}
	
	public void limparTelefone(){
		this.telefone = new Telefone();
	}
	
	public void limparEmail(){
		this.email = new Email();
	}
	
	public void limparNfeConfiguracao(){
		this.nfeConfiguracao = new NfeConfiguracao();
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Telefone getTelefone() {
		return telefone;
	}
	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	public NfeConfiguracao getNfeConfiguracao() {
		return nfeConfiguracao;
	}
	public void setNfeConfiguracao(NfeConfiguracao nfeConfiguracao) {
		this.nfeConfiguracao = nfeConfiguracao;
	}
	public List<EnderecoTipo> getEnderecoTipos() {
		return EnderecoTipos;
	}
	public void setEnderecoTipos(List<EnderecoTipo> enderecoTipos) {
		EnderecoTipos = enderecoTipos;
	}
	public List<Estado> getEstados() {
		return estados;
	}
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	public List<TelefoneTipo> getTelefoneTipos() {
		return telefoneTipos;
	}
	public void setTelefoneTipos(List<TelefoneTipo> telefoneTipos) {
		this.telefoneTipos = telefoneTipos;
	}
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	public List<Email> getEmails() {
		return emails;
	}
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}
	public List<EmailTipo> getEmailTipos() {
		return emailTipos;
	}
	public void setEmailTipos(List<EmailTipo> emailTipos) {
		this.emailTipos = emailTipos;
	}
	public List<NfeConfiguracao> getNfeConfiguracaes() {
		return nfeConfiguracaes;
	}
	public void setNfeConfiguracaes(List<NfeConfiguracao> nfeConfiguracaes) {
		this.nfeConfiguracaes = nfeConfiguracaes;
	}
	public EmpresaService getEmpresaService() {
		return empresaService;
	}
	public void setEmpresaService(EmpresaService empresaService) {
		this.empresaService = empresaService;
	}

	public UsuarioPermissao getUsuarioPermissao() {
		return usuarioPermissao;
	}
	public void setUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		this.usuarioPermissao = usuarioPermissao;
	}	
}
