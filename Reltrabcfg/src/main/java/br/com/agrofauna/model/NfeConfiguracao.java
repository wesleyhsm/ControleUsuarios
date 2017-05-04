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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="nfe_configuracao")
@NamedQueries({
	@NamedQuery(name="NfeConfiguracao.findAll", query="SELECT n FROM NfeConfiguracao n")	
})
public class NfeConfiguracao implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idNfeConfiguracao;
	private Date dtCriacao;
	private Date dtAlteracao;	
	private String nmCaminhoCerticadoDigital;
	private String nmCerticadoDigital;
	private String nmNFeCacerts;
	private String nmCerticadoSenha;	
	private Boolean snStatus;	
	private Pessoa pessoa;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_nfe_configuracao")
	public Long getIdNfeConfiguracao() {
		return idNfeConfiguracao;
	}
	public void setIdNfeConfiguracao(Long idNfeConfiguracao) {
		this.idNfeConfiguracao = idNfeConfiguracao;
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
	@Column(name="nm_caminho_certificado_digital")	
	public String getNmCaminhoCerticadoDigital() {
		return nmCaminhoCerticadoDigital;
	}
	public void setNmCaminhoCerticadoDigital(String nmCaminhoCerticadoDigital) {
		this.nmCaminhoCerticadoDigital = nmCaminhoCerticadoDigital;
	}
	
	@NotBlank
	@Column(name="nm_certificado_digital")
	public String getNmCerticadoDigital() {
		return nmCerticadoDigital;
	}
	public void setNmCerticadoDigital(String nmCerticadoDigital) {
		this.nmCerticadoDigital = nmCerticadoDigital;
	}
	
	@NotBlank
	@Column(name="nm_nfe_cacerts")
	public String getNmNFeCacerts() {
		return nmNFeCacerts;
	}
	public void setNmNFeCacerts(String nmNFeCacerts) {
		this.nmNFeCacerts = nmNFeCacerts;
	}
	
	@NotBlank
	@Column(name="nm_certificado_senha")	
	public String getNmCerticadoSenha() {
		return nmCerticadoSenha;
	}
	public void setNmCerticadoSenha(String nmCerticadoSenha) {
		this.nmCerticadoSenha = nmCerticadoSenha;
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
		return "NfeConfiguracao [idNfeConfiguracao=" + idNfeConfiguracao + ", dtCriacao=" + dtCriacao + ", dtAlteracao="
				+ dtAlteracao + ", nmCerticadoDigital=" + nmCerticadoDigital + ", nmNFeCacerts=" + nmNFeCacerts
				+ ", nmCerticadoSenha=" + nmCerticadoSenha + ", snStatus=" + snStatus + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idNfeConfiguracao == null) ? 0 : idNfeConfiguracao.hashCode());
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
		NfeConfiguracao other = (NfeConfiguracao) obj;
		if (idNfeConfiguracao == null) {
			if (other.idNfeConfiguracao != null)
				return false;
		} else if (!idNfeConfiguracao.equals(other.idNfeConfiguracao))
			return false;
		return true;
	}	
}
