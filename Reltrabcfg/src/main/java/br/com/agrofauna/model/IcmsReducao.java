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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="icms_reducao")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQueries({
	@NamedQuery(name="IcmsReducao.findAll", query="SELECT r FROM IcmsReducao r")	
})
public class IcmsReducao implements Serializable{

	private static final long serialVersionUID = 1L;
		
	private Long idIcmsReducao;	
	private Double nrBaseCalculo;	
	private String nmMensagem;
	private Date dtCriacao;
	private Date dtAlteracao;
	private Boolean snStatus;	
	private List<IcmsEstado> icmsEstados;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_icms_reducao")
	public Long getIdIcmsReducao() {
		return idIcmsReducao;
	}
	public void setIdIcmsReducao(Long idIcmsReducao) {
		this.idIcmsReducao = idIcmsReducao;
	}

	@Column(name="nr_base_calculo")
	public Double getNrBaseCalculo() {
		return nrBaseCalculo;
	}
	public void setNrBaseCalculo(Double nrBaseCalculo) {
		this.nrBaseCalculo = nrBaseCalculo;
	}

	@Column(name="nm_mensagem")
	public String getNmMensagem() {
		return nmMensagem;
	}
	public void setNmMensagem(String nmMensagem) {
		this.nmMensagem = nmMensagem;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao")
	public Date getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

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
	
	@OneToMany(mappedBy="icmsReducao", 
			   fetch = FetchType.LAZY)
	public List<IcmsEstado> getIcmsEstados() {
		return icmsEstados;
	}
	public void setIcmsEstados(List<IcmsEstado> icmsEstados) {
		this.icmsEstados = icmsEstados;
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
		result = prime * result + ((idIcmsReducao == null) ? 0 : idIcmsReducao.hashCode());
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
		IcmsReducao other = (IcmsReducao) obj;
		if (idIcmsReducao == null) {
			if (other.idIcmsReducao != null)
				return false;
		} else if (!idIcmsReducao.equals(other.idIcmsReducao))
			return false;
		return true;
	}	
}