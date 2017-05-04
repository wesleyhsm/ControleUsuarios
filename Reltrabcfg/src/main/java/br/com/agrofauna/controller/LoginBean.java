package br.com.agrofauna.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.agrofauna.model.Funcionario;
import br.com.agrofauna.model.LogStatus;
import br.com.agrofauna.model.Login;
import br.com.agrofauna.model.Sistema;
import br.com.agrofauna.service.LogService;
import br.com.agrofauna.service.LoginService;
import br.com.agrofauna.service.NegocioException;
import br.com.agrofauna.util.FacesUtil;

@Named
@SessionScoped
public class LoginBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Funcionario funcionario;
	private Login login;	
	private String primeiroNomeFuncionario;
	
	@Inject
	private LoginService loginService;
	
	@Inject
	private LogService logService;
		
	@PostConstruct
	public void inicializar(){		
		this.funcionario = new Funcionario();
		this.login =  new Login();		
	}
		
	public String buscarLogin(){
		try{			
			this.funcionario = this.loginService.buscarLogin(this.login);		
			
			this.logService.salvarLog(this.funcionario.getIdPessoa(), 
									  this.funcionario.getLogin().getIdLogin(), 
									  LogStatus.OK, 
									  "LoginBean", 
									  "buscarLogin", 
									  "Funcionario, Login", 
									  Sistema.RELTRABWEB_CFG, 
									  "Logou no Reltrabcfg "+ this.funcionario.toString() + " " + this.funcionario.getLogin().toString());
									
			FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession().setAttribute("funcionario", this.funcionario);
                   
            this.primeiroNomeFuncionario = this.funcionario.getNmFuncionario().substring(0, this.funcionario.getNmFuncionario().indexOf(" "));
            
			return "/admin/home.xhtml";
			
		}catch (NegocioException e) {			
			FacesUtil.addErrorMessage(e.getMessage());
			return "";
			
		}catch (Exception e) {			
			FacesUtil.addErrorMessage("Erro ao logar contatar administrador");
			return "";
		}
	}
	
	public void deslogar(){		
		this.logService.salvarLog(this.funcionario.getIdPessoa(), 
								  this.funcionario.getLogin().getIdLogin(), 
								  LogStatus.OK, 
								  "Funcionario, Login", 
								  "sairLogin", 
								  "Login", 
								  Sistema.RELTRABWEB_CFG,
								  "Deslogou no Reltrabcfg "+ this.funcionario.toString() + " " + this.funcionario.getLogin().toString());
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		session.invalidate();

		try {			
			//facesContext.getExternalContext().redirect("/Reltrabcfg/index.xhtml");
			facesContext.getExternalContext().redirect("/Reltrabcfg-1.0.0-SNAPSHOT/index.xhtml");
		} catch (IOException e){			
		}		
	}	
		
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getPrimeiroNomeFuncionario() {
		return primeiroNomeFuncionario;
	}

	public void setPrimeiroNomeFuncionario(String primeiroNomeFuncionario) {
		this.primeiroNomeFuncionario = primeiroNomeFuncionario;
	}	
}