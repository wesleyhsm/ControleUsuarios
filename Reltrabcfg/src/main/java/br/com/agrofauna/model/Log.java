package br.com.agrofauna.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="log")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Log implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long IdLog;
	private Date dtCriacao;
	private Long IdPessoa;
	private String nmTabela;
	private String mnClasse;
	private String nmMetodo;
	private Long idRegistro;
	private Sistema sistema;
	private LogStatus logStatus;
	private String nmMensagem;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_log")
	public Long getIdLog() {
		return IdLog;
	}
	public void setIdLog(Long idLog) {
		IdLog = idLog;
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
		
	@Column(name="id_pessoa")
	public Long getIdPessoa() {
		return IdPessoa;
	}
	public void setIdPessoa(Long idPessoa) {
		IdPessoa = idPessoa;
	}
	
	@NotBlank
	@Column(name="nm_tabela")
	public String getNmTabela() {
		return nmTabela;
	}
	public void setNmTabela(String nmTabela) {
		this.nmTabela = nmTabela;
	}
	
	@NotBlank
	@Column(name="nm_classe")
	public String getMnClasse() {
		return mnClasse;
	}
	public void setMnClasse(String mnClasse) {
		this.mnClasse = mnClasse;
	}
	
	@NotBlank
	@Column(name="nm_metodo")
	public String getNmMetodo() {
		return nmMetodo;
	}
	public void setNmMetodo(String nmMetodo) {
		this.nmMetodo = nmMetodo;
	}
		
	@Column(name="id_registro")
	public Long getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}
		
	@Enumerated(EnumType.STRING)
	@Column(name="nm_sistema")
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
		
	@Enumerated(EnumType.STRING)
	@Column(name="nm_log_status")
	public LogStatus getLogStatus() {
		return logStatus;
	}
	public void setLogStatus(LogStatus logStatus) {
		this.logStatus = logStatus;
	}
		
	@NotBlank
	@Column(name="nm_mensagem")
	public String getNmMensagem() {
		return nmMensagem;
	}
	public void setNmMensagem(String nmMensagem) {
		this.nmMensagem = nmMensagem;
	}
	
	@PrePersist
	@PreUpdate
	public void configuraDataCriacaoAlteracao(){		
		if(this.dtCriacao == null){
			this.dtCriacao = new Date();
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IdLog == null) ? 0 : IdLog.hashCode());
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
		Log other = (Log) obj;
		if (IdLog == null) {
			if (other.IdLog != null)
				return false;
		} else if (!IdLog.equals(other.IdLog))
			return false;
		return true;
	}	
}
