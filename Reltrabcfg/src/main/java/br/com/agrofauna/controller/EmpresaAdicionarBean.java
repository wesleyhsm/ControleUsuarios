package br.com.agrofauna.controller;

import java.io.Serializable;
import java.util.ArrayList;
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
import br.com.agrofauna.service.NegocioException;
import br.com.agrofauna.service.UsuarioPermissaoService;
import br.com.agrofauna.util.FacesUtil;

@Named
@ViewScoped
public class EmpresaAdicionarBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Empresa empresa;	
	private Endereco endereco;
	private Estado estado;
	private Telefone telefone;
	private Email email;
	private NfeConfiguracao nfeConfiguracao;
	private boolean nomeJanela;
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
	public void inicializar(){
		limparEmpresa();
		limpar();
		limparTelefone();
		limparEmail();
		limparNfeConfiguracao();
		this.estados = this.empresaService.buscarTodosEstados();
		this.EnderecoTipos = Arrays.asList(EnderecoTipo.values());
		this.telefoneTipos = Arrays.asList(TelefoneTipo.values());
		this.emailTipos = Arrays.asList(EmailTipo.values());
		this.usuarioPermissao =  this.usuarioPermissaoService.pesquisarPermissao(this.loginBean, 6L);
		
		try{
			this.empresa.setIdPessoa( Long.parseLong( FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id") ) );
			this.empresa = this.empresaService.buscarEmpresaPeloId(this.empresa);
			this.enderecos = this.empresaService.buscarEnderecoPeloId(this.empresa);			
			this.telefones =  this.empresaService.buscarTelefonePeloId(this.empresa);			
			this.emails = this.empresaService.buscarEmailPeloId(this.empresa);			
			this.nfeConfiguracaes = this.empresaService.buscarNfeConfiguracaoPeloId(this.empresa);
			this.nomeJanela = true;
			
			this.logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
					 this.empresa.getIdPessoa(), 
					 LogStatus.OK, 
					 "EmpresaAdicionarBean",
					 "editar", 
					 "empresa",
					 Sistema.RELTRABWEB_CFG,
					 "Empresa antes de editar: "+ this.empresa.toString());
			
			for(Email email: emails){
				this.logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
						 email.getIdEmail(), 
						 LogStatus.OK, 
						 "EmpresaAdicionarBean",
						 "editar", 
						 "E-mails",
						 Sistema.RELTRABWEB_CFG,
						 "E-mail antes de editar: "+ email.toString());
			}		
						
			for(Endereco endereco: enderecos){
				this.logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
						 endereco.getIdEndereco(), 
						 LogStatus.OK, 
						 "EmpresaAdicionarBean",
						 "editar", 
						 "Endereços",
						 Sistema.RELTRABWEB_CFG,
						 "Endreço antes de editar: "+ endereco.toString());
			}
						
			for(Telefone telefone: telefones){
				this.logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
						 telefone.getIdTelefone(), 
						 LogStatus.OK, 
						 "EmpresaAdicionarBean",
						 "editar", 
						 "Telefones",
						 Sistema.RELTRABWEB_CFG,
						 "Telefone antes de editar: "+ telefone.toString());
			}
						
			for(NfeConfiguracao nfe: nfeConfiguracaes){
				this.logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
						 nfe.getIdNfeConfiguracao(), 
						 LogStatus.OK, 
						 "EmpresaAdicionarBean",
						 "editar", 
						 "NfeConfiguração",
						 Sistema.RELTRABWEB_CFG,
						 "NF-e Configuração antes de editar: "+ nfe.toString());
			}
		}catch (Exception e) {
			e.printStackTrace();
			limparEmpresa();
			this.enderecos = new ArrayList<Endereco>();		
			this.telefones =  new ArrayList<Telefone>();			
			this.emails = new ArrayList<Email>();			
			this.nfeConfiguracaes = new ArrayList<NfeConfiguracao>();
		}	
	}
	
	public void limpar(){		
		this.endereco = new Endereco();
		this.estado = new Estado();
	}
	
	public void limparEmpresa(){		
		this.empresa = new Empresa();
		this.nomeJanela = false; 
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
	
	public void salvar(){
		try{
			if(this.empresaService.salvarEmpresa(this.empresa, this.enderecos, this.telefones, this.emails, this.nfeConfiguracaes) != null){
				FacesUtil.addInfoMessage("Empresa salvo com sucesso.");
				
				this.logService.salvarLog(this.loginBean.getFuncionario().getIdPessoa(), 
						 this.empresa.getIdPessoa(), 
						 LogStatus.OK, 
						 "EmpresaAdicionarBean",
						 "Adicionar", 
						 "empresa",
						 Sistema.RELTRABWEB_CFG,
						 "Empresa adicionar / editar: "+ this.empresa.toString());
				
				limpar();
				limparEmpresa();			
				limparTelefone();
				limparEmail();
				limparNfeConfiguracao();
			}else{
				FacesUtil.addErrorMessage("Erro ao salvar, verifique os dados digitados.");
			}			
		}catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao salvar empresa.");
		}
		
	}
	
	public void atualizarEndereco(){
		this.endereco = this.empresaService.encontraCEP(this.endereco);	
		this.estado = this.endereco.getEstado();
	}
	
	public void adicionaEmpresaNaLista(){		
		FacesUtil.addInfoMessage("Empresa adicionado com sucesso.");
	}
	
	public void adicionaEnderecoNaLista(){
		this.endereco.setEstado(this.estado);
		this.enderecos.add(this.endereco);		
		limpar();
		FacesUtil.addInfoMessage("Endereço adicionado com sucesso.");
	}
	
	public void editarEnderecoNaLista(Endereco endereco){
		this.endereco = endereco;
		this.estado = endereco.getEstado();
		this.enderecos.remove(endereco);
		FacesUtil.addInfoMessage("Endereço selecionado com sucesso.");
	}
	
	public void excluirEnderecoNalista(Endereco endereco){
		this.enderecos.remove(endereco);
		FacesUtil.addInfoMessage("Endereço removido com sucesso.");
	}
	
	public void adicionarTelefoneNaLista(){
		this.telefone.setNmContato("Empresa");
		this.telefones.add(this.telefone);
		limparTelefone();
		FacesUtil.addInfoMessage("Telefone adicionado com sucesso.");
	}
	
	public void editarTelefoneNaLista(Telefone telefone){
		this.telefone = telefone;
		this.telefones.remove(telefone);
		FacesUtil.addInfoMessage("Telefone selecionado com sucesso.");
	}
	
	public void excluirTelefoneNaLista(Telefone telefone){
		this.telefones.remove(telefone);
		FacesUtil.addInfoMessage("Telefone removido com sucesso.");
	}
	
	public void adicionarEmailNaLista(){
		this.email.setNmContato("Empresa");
		this.emails.add(this.email);
		limparEmail();
		FacesUtil.addInfoMessage("E-mail adicionado com sucesso.");
	}
	
	public void editarEmailNaLista(Email email){
		this.email = email;
		this.emails.remove(email);
		FacesUtil.addInfoMessage("E-mail selecionado com sucesso.");
	}
	
	public void excluirEmailNaLista(Email email){
		this.emails.remove(email);
		FacesUtil.addInfoMessage("E-mail removido com sucesso.");
	}
	
	public void adicionarNfeConfiguracaoNaLista(){
		this.nfeConfiguracaes.add(this.nfeConfiguracao);
		limparNfeConfiguracao();;
		FacesUtil.addInfoMessage("Nf-e Configuração adicionado com sucesso.");
	}
	
	public void editarNfeConfiguracaoNaLista(NfeConfiguracao nfeConfiguracao){
		this.nfeConfiguracao = nfeConfiguracao;
		this.nfeConfiguracaes.remove(nfeConfiguracao);
		FacesUtil.addInfoMessage("Nf-e Configuração selecionado com sucesso.");
	}
	
	public void excluirNfeConfiguracaoNaLista(NfeConfiguracao nfeConfiguracao){
		this.nfeConfiguracaes.remove(nfeConfiguracao);
		FacesUtil.addInfoMessage("Nf-e Configuração removido com sucesso.");
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<EnderecoTipo> getEnderecoTipos() {
		return EnderecoTipos;
	}
	public void setEnderecoTipos(List<EnderecoTipo> enderecoTipos) {
		EnderecoTipos = enderecoTipos;
	}

	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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

	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<TelefoneTipo> getTelefoneTipos() {
		return telefoneTipos;
	}
	public void setTelefoneTipos(List<TelefoneTipo> telefoneTipos) {
		this.telefoneTipos = telefoneTipos;
	}

	public Telefone getTelefone() {
		return telefone;
	}
	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
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

	public NfeConfiguracao getNfeConfiguracao() {
		return nfeConfiguracao;
	}
	public void setNfeConfiguracao(NfeConfiguracao nfeConfiguracao) {
		this.nfeConfiguracao = nfeConfiguracao;
	}

	public List<NfeConfiguracao> getNfeConfiguracaes() {
		return nfeConfiguracaes;
	}
	public void setNfeConfiguracaes(List<NfeConfiguracao> nfeConfiguracaes) {
		this.nfeConfiguracaes = nfeConfiguracaes;
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