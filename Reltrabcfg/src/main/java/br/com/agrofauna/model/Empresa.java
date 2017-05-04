package br.com.agrofauna.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import br.com.agrofauna.model.Pessoa;

@Entity
@Table(name="empresa")
@PrimaryKeyJoinColumn(name="id_pessoa")
@DiscriminatorValue("EMPRESA")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQueries({
	@NamedQuery(name="Empresa.todos", query="SELECT e FROM Empresa e"),
	@NamedQuery(name="Empresa.existeCnpj", query="SELECT e FROM Empresa e WHERE e.nmCnpjCpf = :cnpjcpf"),
	@NamedQuery(name="Empresa.id", query="SELECT e FROM Empresa e WHERE e.idPessoa=:idPessoa"),
	@NamedQuery(name="Empresa.email", query="SELECT e FROM Email e WHERE e.pessoa.idPessoa=:idPessoa"),
	@NamedQuery(name="Empresa.endereco", query="SELECT e FROM Endereco e join fetch e.estado WHERE e.pessoa.idPessoa=:idPessoa"),
	@NamedQuery(name="Empresa.telefone", query="SELECT e FROM Telefone e WHERE e.pessoa.idPessoa=:idPessoa"),
	@NamedQuery(name="Empresa.nfeConfiguraor", query="SELECT e FROM NfeConfiguracao e WHERE e.pessoa.idPessoa=:idPessoa"),
	@NamedQuery(name="Empresa.emailExcluir", query="DELETE FROM Email e WHERE e.pessoa.idPessoa=:idPessoa"),
	@NamedQuery(name="Empresa.enderecoExcluir", query="DELETE FROM Endereco e WHERE e.pessoa.idPessoa=:idPessoa"),
	@NamedQuery(name="Empresa.telefoneExcluir", query="DELETE FROM Telefone e WHERE e.pessoa.idPessoa=:idPessoa"),
	@NamedQuery(name="Empresa.nfeConfiguraorExcluir", query="DELETE FROM NfeConfiguracao e WHERE e.pessoa.idPessoa=:idPessoa"),
	@NamedQuery(name="Empresa.funcionarioEmpresas", query="SELECT e FROM Empresa e join e.usuarioEmpresas u WHERE u.usuario.idUsuario=:idUsuario")
})
public class Empresa extends Pessoa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nmRazaoSocial;
	private String nmFantasia;
	private String nmFilial;
	private String nmInscricaoEstadual;
	private Date dtFundacao;
	private Date dtCriacao;
	private Date dtAlteracao;
	private Boolean snStatus = true;	
	private List<UsuarioEmpresa> usuarioEmpresas;
	
	@NotBlank
	@Column(name="nm_razao_social")	
	public String getNmRazaoSocial() {
		return nmRazaoSocial;
	}
	public void setNmRazaoSocial(String nmRazaoSocial) {
		this.nmRazaoSocial = nmRazaoSocial;
	}
	
	@NotBlank
	@Column(name="nm_fantasia")
	public String getNmFantasia() {
		return nmFantasia;
	}
	public void setNmFantasia(String nmFantasia) {
		this.nmFantasia = nmFantasia;
	}
	
	@NotBlank
	@Column(name="nm_filial")
	public String getNmFilial() {
		return nmFilial;
	}
	public void setNmFilial(String nmFilial) {
		this.nmFilial = nmFilial;
	}
		
	@Column(name="nm_inscricao_estadual")
	public String getNmInscricaoEstadual() {
		return nmInscricaoEstadual;
	}
	public void setNmInscricaoEstadual(String nmInscricaoEstadual) {
		this.nmInscricaoEstadual = nmInscricaoEstadual;
	}	
	
	@NotNull 
	@Temporal(TemporalType.DATE)
	@Column(name="dt_fundacao")
	public Date getDtFundacao() {
		return dtFundacao;
	}
	public void setDtFundacao(Date dtFundacao) {
		this.dtFundacao = dtFundacao;
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
		
	@Column(name="sn_status")
	public Boolean getSnStatus() {
		return snStatus;
	}
	public void setSnStatus(Boolean snStatus) {
		this.snStatus = snStatus;
	}
		
	@OneToMany(mappedBy="empresa", fetch=FetchType.LAZY)
	public List<UsuarioEmpresa> getUsuarioEmpresas() {
		return usuarioEmpresas;
	}
	public void setUsuarioEmpresas(List<UsuarioEmpresa> usuarioEmpresas) {
		this.usuarioEmpresas = usuarioEmpresas;
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
		return "Empresa [nmRazaoSocial=" + nmRazaoSocial + ", nmFantasia=" + nmFantasia + ", nmFilial=" + nmFilial
				+ ", nmInscricaoEstadual=" + nmInscricaoEstadual + ", dtFundacao=" + dtFundacao + ", dtCriacao="
				+ dtCriacao + ", dtAlteracao=" + dtAlteracao + ", snStatus=" + snStatus + "]";
	}	
}
