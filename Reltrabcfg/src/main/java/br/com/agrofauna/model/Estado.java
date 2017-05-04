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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="estado")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQueries({
	@NamedQuery(name="Estado.findAll", query="SELECT e FROM Estado e ORDER BY e.nmEstado ASC"),
	@NamedQuery(name="Estado.sigla", query="SELECT e FROM Estado e WHERE e.sgEstado=:nmSigla"),
})	
public class Estado implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idEstado;	
	private String nmEstado;
	private String sgEstado;	
	private String nmIbgeEstato;	
	private Double nrIcms;
	private Date dtCriacao;
	private Date dtAlteracao;
	private Boolean snStatus;
	private List<Endereco> enderecos;
	private Pais pais;
	private List<IcmsEstado> icmsEstadoOrigems;	
	private List<IcmsEstado> icmsEstadoDestinos;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_estado")
	public Long getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	@NotBlank @Max(value=60) 
	@Column(name="nm_estado", length=60)
	public String getNmEstado() {
		return nmEstado;
	}
	public void setNmEstado(String nmEstado) {
		this.nmEstado = nmEstado;
	}

	@NotBlank @Max(value=2)
	@Column(name="sg_estado", length=2)
	public String getSgEstado() {
		return sgEstado;
	}
	public void setSgEstado(String sgEstado) {
		this.sgEstado = sgEstado;
	}

	@NotBlank @Max(value=15)
	@Column(name="nm_ibge_estado", length=15)
	public String getNmIbgeEstato() {
		return nmIbgeEstato;
	}
	public void setNmIbgeEstato(String nmIbgeEstato) {
		this.nmIbgeEstato = nmIbgeEstato;
	}

	@NotNull
	@Column(name="nr_icms")
	public Double getNrIcms() {
		return nrIcms;
	}
	public void setNrIcms(Double nrIcms) {
		this.nrIcms = nrIcms;
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
	
	@OneToMany(mappedBy="estado", fetch = FetchType.LAZY)
	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pais")
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	@OneToMany(mappedBy="estadoOrigem", fetch = FetchType.LAZY)
	public List<IcmsEstado> getIcmsEstadoOrigems() {
		return icmsEstadoOrigems;
	}
	public void setIcmsEstadoOrigems(List<IcmsEstado> icmsEstadoOrigems) {
		this.icmsEstadoOrigems = icmsEstadoOrigems;
	}

	@OneToMany(mappedBy="estadoDestino", fetch = FetchType.LAZY)
	public List<IcmsEstado> getIcmsEstadoDestinos() {
		return icmsEstadoDestinos;
	}
	public void setIcmsEstadoDestinos(List<IcmsEstado> icmsEstadoDestinos) {
		this.icmsEstadoDestinos = icmsEstadoDestinos;
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
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
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
		Estado other = (Estado) obj;
		if (idEstado == null) {
			if (other.idEstado != null)
				return false;
		} else if (!idEstado.equals(other.idEstado))
			return false;
		return true;
	}		
}
