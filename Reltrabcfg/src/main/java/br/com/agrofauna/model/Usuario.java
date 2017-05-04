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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="usuario")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQueries({
	@NamedQuery(name="Usuario.todos", query="SELECT u FROM Usuario u ORDER BY u.nmUsuario asc"),
	@NamedQuery(name="Usuario.peloCodigo", query="SELECT u FROM Usuario u WHERE u.idUsuario=:idUsuario"),
	@NamedQuery(name="Usuario.existeUsuario", query="SELECT u FROM Usuario u WHERE u.nmUsuario like :nomeUsuario"),
	@NamedQuery(name="Usuario.verificaUsuario", query="SELECT u FROM Usuario u WHERE u.idUsuario=:idUsuario and u.funcionario.idPessoa is not null")	
})
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idUsuario;
	private String nmUsuario;
	private Date dtCriacao; 
	private Date dtAlteracao;
	private Boolean snStatus;
	private Funcionario funcionario;
	private List<UsuarioPermissao> usuarioPermissoes;
	private List<UsuarioEmpresa> usuarioEmpresas; 
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	@NotBlank
	@Column(name="nm_usuario")
	public String getNmUsuario() {
		return nmUsuario;
	}
	public void setNmUsuario(String nmUsuario) {
		this.nmUsuario = nmUsuario;
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
		
	@OneToMany(mappedBy="usuario", fetch=FetchType.LAZY)	
	public List<UsuarioPermissao> getUsuarioPermissoes() {
		return usuarioPermissoes;
	}
	public void setUsuarioPermissoes(List<UsuarioPermissao> usuarioPermissoes) {
		this.usuarioPermissoes = usuarioPermissoes;
	}
		
	@OneToMany(mappedBy="usuario", fetch=FetchType.LAZY)
	public List<UsuarioEmpresa> getUsuarioEmpresas() {
		return usuarioEmpresas;
	}
	public void setUsuarioEmpresas(List<UsuarioEmpresa> usuarioEmpresas) {
		this.usuarioEmpresas = usuarioEmpresas;
	}
	
	@OneToOne(fetch = FetchType.LAZY, optional=true)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="id_pessoa")
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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
		return "Usuario [idUsuario=" + idUsuario + ", nmUsuario=" + nmUsuario + ", dtCriacao=" + dtCriacao + ", dtAlteracao=" + dtAlteracao + ", snStatus=" + snStatus + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}	
}
