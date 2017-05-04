package br.com.agrofauna.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="permissao")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQueries({
	@NamedQuery(name="Permissao.todos", query="SELECT p FROM Permissao p ORDER BY p.nmJanela asc"),
	@NamedQuery(name="Permissao.usuarioCadastrado", query="SELECT p FROM Permissao p inner join p.usuarioPermissoes up inner join up.usuario u WHERE u.idUsuario = :idUsuario ORDER BY p.nmJanela asc"),
	@NamedQuery(name="Permissao.peloCodigo", query="SELECT p FROM Permissao p WHERE p.idPermissao=:id"),
	@NamedQuery(name="Permissao.existePermissao", query="SELECT p FROM Permissao p WHERE p.nmJanela like :janela")	
})
public class Permissao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long idPermissao;
	private String nmJanela;
	private Date dtCriacao; 
	private Date dtAlteracao;
	private Boolean snStatus;
	private List<UsuarioPermissao> usuarioPermissoes;
	private PermissaoSetor permissaoSetor;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_permissao")
	public Long getIdPermissao() {
		return idPermissao;
	}
	public void setIdPermissao(Long idPermissao) {
		this.idPermissao = idPermissao;
	}
	
	@NotBlank
	@Column(name="nm_janela")	
	public String getNmJanela() {
		return nmJanela;
	}
	public void setNmJanela(String nmJanela) {
		this.nmJanela = nmJanela;
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
	
	@OneToMany(mappedBy="permissao", fetch = FetchType.LAZY)	
	public List<UsuarioPermissao> getUsuarioPermissoes() {
		return usuarioPermissoes;
	}
	public void setUsuarioPermissoes(List<UsuarioPermissao> usuarioPermissoes) {
		this.usuarioPermissoes = usuarioPermissoes;
	}
		
	@Enumerated(EnumType.STRING)
	@Column(name="nm_permissao_setor")
	public PermissaoSetor getPermissaoSetor() {
		return permissaoSetor;
	}
	public void setPermissaoSetor(PermissaoSetor permissaoSetor) {
		this.permissaoSetor = permissaoSetor;
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
		return "Permissao [idPermissao=" + idPermissao + ", nmJanela=" + nmJanela + ", dtCriacao=" + dtCriacao
				+ ", dtAlteracao=" + dtAlteracao + ", snStatus=" + snStatus + ", permissaoSetor=" + permissaoSetor
				+ "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPermissao == null) ? 0 : idPermissao.hashCode());
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
		Permissao other = (Permissao) obj;
		if (idPermissao == null) {
			if (other.idPermissao != null)
				return false;
		} else if (!idPermissao.equals(other.idPermissao))
			return false;
		return true;
	}	
}
