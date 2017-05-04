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
@Table(name="telefone")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQueries({
	@NamedQuery(name="Telefone.findAll", query="SELECT t FROM Telefone t")	
})	
public class Telefone implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long idTelefone;
	private String nmTelefone;
	private String nmContato;
	private Date dtCriacao;
	private Date dtAlteracao;
	private Boolean snStatus;
	private Pessoa pessoa;
	private TelefoneTipo telefoneTipo;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_telefone")
	public Long getIdTelefone() {
		return idTelefone;
	}
	public void setIdTelefone(Long idTelefone) {
		this.idTelefone = idTelefone;
	}
	
	@NotBlank
	@Column(name="nm_telefone")
	public String getNmTelefone() {
		return nmTelefone;
	}
	public void setNmTelefone(String nmTelefone) {
		this.nmTelefone = nmTelefone;
	}
	
	@NotBlank
	@Column(name="nm_contato")
	public String getNmContato() {
		return nmContato;
	}
	public void setNmContato(String nmContato) {
		this.nmContato = nmContato;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
		
	@Enumerated(EnumType.STRING)
	@Column(name="nm_telefone_tipo")
	public TelefoneTipo getTelefoneTipo() {
		return telefoneTipo;
	}
	public void setTelefoneTipo(TelefoneTipo telefoneTipo) {
		this.telefoneTipo = telefoneTipo;
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
		return "Telefone [idTelefone=" + idTelefone + ", nmTelefone=" + nmTelefone + ", nmContato=" + nmContato
				+ ", dtCriacao=" + dtCriacao + ", dtAlteracao=" + dtAlteracao + ", snStatus=" + snStatus
				+ ", telefoneTipo=" + telefoneTipo + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTelefone == null) ? 0 : idTelefone.hashCode());
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
		Telefone other = (Telefone) obj;
		if (idTelefone == null) {
			if (other.idTelefone != null)
				return false;
		} else if (!idTelefone.equals(other.idTelefone))
			return false;
		return true;
	}	
}
