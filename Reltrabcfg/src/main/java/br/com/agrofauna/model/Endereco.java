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
@Table(name="endereco")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQueries({	
	@NamedQuery(name="Endereco.findAll", query="SELECT e FROM Endereco e")
})
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idEndereco;		
	private String nmBairro;		
	private String nmCaixaPostal;		
	private String nmCep;	
	private String nmRua;	
	private String nmCidade;		
	private String nmNumero;	
	private String nmComplemento;	
	private Pessoa pessoa;
	private Estado estado;
	private Date dtCriacao;
	private Date dtAlteracao;
	private EnderecoTipo enderecoTipo; 
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_endereco")
	public Long getIdEndereco() {
		return idEndereco;
	}
	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}
	
	@NotBlank
	@Column(name="nm_bairro")
	public String getNmBairro() {
		return nmBairro;
	}
	public void setNmBairro(String nmBairro) {
		this.nmBairro = nmBairro;
	}
	
	@Column(name="nm_caixa_postal")
	public String getNmCaixaPostal() {
		return nmCaixaPostal;
	}
	public void setNmCaixaPostal(String nmCaixaPostal) {
		this.nmCaixaPostal = nmCaixaPostal;
	}
	
	@NotBlank
	@Column(name="nm_cep")
	public String getNmCep() {
		return nmCep;
	}
	public void setNmCep(String nmCep) {
		this.nmCep = nmCep;
	}
	
	@NotBlank
	@Column(name="nm_rua")
	public String getNmRua() {
		return nmRua;
	}
	public void setNmRua(String nmRua) {
		this.nmRua = nmRua;
	}
	
	@NotBlank
	@Column(name="nm_cidade")
	public String getNmCidade() {
		return nmCidade;
	}
	public void setNmCidade(String nmCidade) {
		this.nmCidade = nmCidade;
	}
	
	@NotBlank
	@Column(name="nm_numero")
	public String getNmNumero() {
		return nmNumero;
	}
	public void setNmNumero(String nmNumero) {
		this.nmNumero = nmNumero;
	}
	
	@Column(name="nm_complemento")
	public String getNmComplemento() {
		return nmComplemento;
	}
	public void setNmComplemento(String nmComplemento) {
		this.nmComplemento = nmComplemento;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pessoa")		
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_estado")
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
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
	
	@Enumerated(EnumType.STRING)
	@Column(name="nm_endereco_tipo")	
	public EnderecoTipo getEnderecoTipo() {
		return enderecoTipo;
	}
	public void setEnderecoTipo(EnderecoTipo enderecoTipo) {
		this.enderecoTipo = enderecoTipo;
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
		return "Endereco [idEndereco=" + idEndereco + ", nmBairro=" + nmBairro + ", nmCaixaPostal=" + nmCaixaPostal
				+ ", nmCep=" + nmCep + ", nmRua=" + nmRua + ", nmCidade=" + nmCidade + ", nmNumero=" + nmNumero
				+ ", nmComplemento=" + nmComplemento + ", dtCriacao=" + dtCriacao + ", dtAlteracao=" + dtAlteracao
				+ ", enderecoTipo=" + enderecoTipo + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEndereco == null) ? 0 : idEndereco.hashCode());
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
		Endereco other = (Endereco) obj;
		if (idEndereco == null) {
			if (other.idEndereco != null)
				return false;
		} else if (!idEndereco.equals(other.idEndereco))
			return false;
		return true;
	}	
}

	