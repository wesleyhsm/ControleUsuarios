package br.com.agrofauna.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="login")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQueries({
	@NamedQuery(name="Login.findAll", query="SELECT l FROM Login l"),	
	@NamedQuery(name="Login.funcionario", query="SELECT f FROM Funcionario f join fetch f.login join fetch f.usuario WHERE f.login.nmLogin=:login AND f.login.nmSenha=:senha AND f.login.snStatus=true AND f.snStatus=true")	
})
public class Login implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idLogin;		
	private Date dtCriacao;
	private Date dtAlteracao;
	private String nmLogin;		
	private String nmSenha;		
	private Boolean snStatus;	
	private Pessoa pessoa;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_login")
	public Long getIdLogin() {
		return idLogin;
	}
	public void setIdLogin(Long idLogin) {
		this.idLogin = idLogin;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	public Date getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_alteracao")	
	public Date getDtAlteracao() {
		return dtAlteracao;
	}
	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}
	
	@NotBlank
	@Column(name="nm_login")
	public String getNmLogin() {
		return nmLogin;
	}
	public void setNmLogin(String nmLogin) {
		this.nmLogin = nmLogin;
	}

	@NotBlank
	@Column(name="nm_senha")
	public String getNmSenha() {
		return nmSenha;
	}
	public void setNmSenha(String nmSenha) {
		this.nmSenha = nmSenha;
	}

	@NotNull
	@Column(name="sn_status")
	public Boolean getSnStatus() {
		return snStatus;
	}
	public void setSnStatus(Boolean snStatus) {
		this.snStatus = snStatus;
	}

	@OneToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="id_pessoa")
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	@PrePersist
	@PreUpdate
	public void configuraDataCriacaoAlteracao(){
		this.dtAlteracao = new Date();
		
		if(this.dtCriacao == null){
			this.dtCriacao = new Date();
		}
	}
		
	
	
	@Override
	public String toString() {
		return "Login [idLogin=" + idLogin + ", dtCriacao=" + dtCriacao + ", dtAlteracao=" + dtAlteracao + ", nmLogin="
				+ nmLogin + ", nmSenha=" + nmSenha + ", snStatus=" + snStatus + ", pessoa=" + pessoa.getIdPessoa() + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLogin == null) ? 0 : idLogin.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Login other = (Login) obj;
		if (idLogin == null) {
			if (other.idLogin != null)
				return false;
		} else if (!idLogin.equals(other.idLogin))
			return false;
		return true;
	}	
}
