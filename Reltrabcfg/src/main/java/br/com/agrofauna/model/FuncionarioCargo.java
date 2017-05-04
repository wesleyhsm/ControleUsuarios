package br.com.agrofauna.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name="funcionario_cargo")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class FuncionarioCargo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idFuncionarioCargo;
	private String nmFuncionarioCargo;
	private Date dtCriacao;
	private Date dtAlteracao;	
	private Boolean snStatus;	
	private List<Funcionario> funcionarios;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_funcionario_cargo")
	public Long getIdFuncionarioCargo() {
		return idFuncionarioCargo;
	}
	public void setIdFuncionarioCargo(Long idFuncionarioCargo) {
		this.idFuncionarioCargo = idFuncionarioCargo;
	}
	
	@NotBlank
	@Column(name="nm_funcioanrio_cargo")
	public String getNmFuncionarioCargo() {
		return nmFuncionarioCargo;
	}
	public void setNmFuncionarioCargo(String nmFuncionarioCargo) {
		this.nmFuncionarioCargo = nmFuncionarioCargo;
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
		
	@OneToMany(mappedBy="funcionarioCargo", fetch = FetchType.LAZY)
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
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
		result = prime * result + ((idFuncionarioCargo == null) ? 0 : idFuncionarioCargo.hashCode());
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
		FuncionarioCargo other = (FuncionarioCargo) obj;
		if (idFuncionarioCargo == null) {
			if (other.idFuncionarioCargo != null)
				return false;
		} else if (!idFuncionarioCargo.equals(other.idFuncionarioCargo))
			return false;
		return true;
	}	
}
