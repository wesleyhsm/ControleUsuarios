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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="empresa_suframa")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class EmpresaSuframa implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long idEmpresaSuframa;
	private String nmEmpresaSuframa;
	private Date dtCriacao;
	private Date dtAlteracao;
	private Boolean snStatus;
	private Empresa empresa;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_empresa_suframa")
	public Long getIdEmpresaSuframa() {
		return idEmpresaSuframa;
	}
	public void setIdEmpresaSuframa(Long idEmpresaSuframa) {
		this.idEmpresaSuframa = idEmpresaSuframa;
	}
	
	@Column(name="nm_empresa_suframa")
	public String getNmEmpresaSuframa() {
		return nmEmpresaSuframa;
	}
	public void setNmEmpresaSuframa(String nmEmpresaSuframa) {
		this.nmEmpresaSuframa = nmEmpresaSuframa;
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
	
	@NotNull
	@Column(name="sn_status")
	public Boolean getSnStatus() {
		return snStatus;
	}
	public void setSnStatus(Boolean snStatus) {
		this.snStatus = snStatus;
	}
		
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_empresa")
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEmpresaSuframa == null) ? 0 : idEmpresaSuframa.hashCode());
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
		EmpresaSuframa other = (EmpresaSuframa) obj;
		if (idEmpresaSuframa == null) {
			if (other.idEmpresaSuframa != null)
				return false;
		} else if (!idEmpresaSuframa.equals(other.idEmpresaSuframa))
			return false;
		return true;
	}	
}
