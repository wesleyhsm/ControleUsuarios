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
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="icms_estado")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class IcmsEstado implements Serializable{

	private static final long serialVersionUID = 1L;
		
	private Long idIcmsEstado;	
	private Date dtCriacao;
	private Date dtAlteracao;
	private Boolean snStatus;	
	private Double nrAlicota;
	private Estado estadoOrigem;
	private Estado estadoDestino;
	private IcmsReducao icmsReducao;
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_icms_estado")
	public Long getIdIcmsEstado() {
		return idIcmsEstado;
	}
	public void setIdIcmsEstado(Long idIcmsEstado) {
		this.idIcmsEstado = idIcmsEstado;
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

	@NotNull
	@Column(name="nr_alicota")
	public Double getNrAlicota() {
		return nrAlicota;
	}
	public void setNrAlicota(Double nrAlicota) {
		this.nrAlicota = nrAlicota;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_estado_origem")
	public Estado getEstadoOrigem() {
		return estadoOrigem;
	}
	public void setEstadoOrigem(Estado estadoOrigem) {
		this.estadoOrigem = estadoOrigem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_estado_destino")
	public Estado getEstadoDestino() {
		return estadoDestino;
	}
	public void setEstadoDestino(Estado estadoDestino) {
		this.estadoDestino = estadoDestino;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_icms_reducao")
	public IcmsReducao getIcmsReducao() {
		return icmsReducao;
	}
	public void setIcmsReducao(IcmsReducao icmsReducao) {
		this.icmsReducao = icmsReducao;
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
		result = prime * result + ((idIcmsEstado == null) ? 0 : idIcmsEstado.hashCode());
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
		IcmsEstado other = (IcmsEstado) obj;
		if (idIcmsEstado == null) {
			if (other.idIcmsEstado != null)
				return false;
		} else if (!idIcmsEstado.equals(other.idIcmsEstado))
			return false;
		return true;
	}	
}
