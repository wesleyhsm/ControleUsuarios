package br.com.agrofauna.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name="email")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQueries({
	@NamedQuery(name="Email.findAll", query="SELECT e FROM Email e")	
})
public class Email implements Serializable{
	
	private static final long serialVersionUID = 1L;
			
	private Long idEmail;
	private Date dtCriacao;
	private Date dtAlteracao;	
	private String nmContato;	
	private String nmEmail;	
	private Boolean snStatus;	
	private Pessoa pessoa;
	private EmailTipo emailTipo;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_email")
	public Long getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(Long idEmail) {
		this.idEmail = idEmail;
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
	@Column(name="nm_contato")
	public String getNmContato() {
		return nmContato;
	}
	public void setNmContato(String nmContato) {
		this.nmContato = nmContato;
	}

	@NotBlank
	@org.hibernate.validator.constraints.Email(message="não é e-mail válido.")
	@Column(name="nm_email")
	public String getNmEmail() {
		return nmEmail;
	}
	public void setNmEmail(String nmEmail) {
		this.nmEmail = nmEmail;
	}

	@NotNull
	@Column(name="sn_status")
	public Boolean getSnStatus() {
		return snStatus;
	}
	public void setSnStatus(Boolean snStatus) {
		this.snStatus = snStatus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="nm_email_tipo")		
	public EmailTipo getEmailTipo() {
		return emailTipo;
	}
	public void setEmailTipo(EmailTipo emailTipo) {
		this.emailTipo = emailTipo;
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
		return "Email [idEmail=" + idEmail + ", dtCriacao=" + dtCriacao + ", dtAlteracao=" + dtAlteracao
				+ ", nmContato=" + nmContato + ", nmEmail=" + nmEmail + ", snStatus=" + snStatus 
				+ ", emailTipo=" + emailTipo + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEmail == null) ? 0 : idEmail.hashCode());
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
		Email other = (Email) obj;
		if (idEmail == null) {
			if (other.idEmail != null)
				return false;
		} else if (!idEmail.equals(other.idEmail))
			return false;
		return true;
	}	
}
