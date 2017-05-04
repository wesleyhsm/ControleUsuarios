package br.com.agrofauna.model;

import java.io.Serializable;

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
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="usuario_permissao")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedQueries({
	@NamedQuery(name="UsuarioPermissao.findAll", query="SELECT fp FROM UsuarioPermissao fp"),
	@NamedQuery(name="UsuarioPermissao.excluirPermissaoUsuario", query="DELETE FROM UsuarioPermissao fp WHERE fp.usuario.idUsuario=:idUsuario"),
	@NamedQuery(name="UsuarioPermissao.buscarPermissao", query="SELECT up FROM UsuarioPermissao up join fetch up.permissao WHERE up.permissao.idPermissao=:idPermissao"),
	@NamedQuery(name="UsuarioPermissao.listPermissaoUsuario", query="SELECT up FROM UsuarioPermissao up join fetch up.permissao join fetch up.usuario WHERE up.usuario.idUsuario=:idUsuario"),
	@NamedQuery(name="UsuarioPermissao.listPemmisaoFuncionario", query="SELECT fp FROM UsuarioPermissao fp join fp.usuario u join u.funcionario f WHERE f.idPessoa=:idPessoa AND f.snStatus=true AND u.snStatus=true")
})
public class UsuarioPermissao implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idUsuarioPermissao;
	private Boolean snAdicionar;
	private Boolean snEditar;	
	private Boolean snRemover;
	private Boolean snVisualizar;	
	private Permissao permissao;
	private Usuario usuario;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario_permissao")	
	public Long getIdUsuarioPermissao() {
		return idUsuarioPermissao;
	}
	public void setIdUsuarioPermissao(Long idUsuarioPermissao) {
		this.idUsuarioPermissao = idUsuarioPermissao;
	}
		
	@Column(name="sn_adicionar")
	public Boolean getSnAdicionar() {
		return snAdicionar;
	}
	public void setSnAdicionar(Boolean snAdicionar) {
		this.snAdicionar = snAdicionar;
	}
		
	@Column(name="sn_editar")
	public Boolean getSnEditar() {
		return snEditar;
	}
	public void setSnEditar(Boolean snEditar) {
		this.snEditar = snEditar;
	}
		
	@Column(name="sn_remover")
	public Boolean getSnRemover() {
		return snRemover;
	}
	public void setSnRemover(Boolean snRemover) {
		this.snRemover = snRemover;
	}
		
	@Column(name="sn_visualizar")
	public Boolean getSnVisualizar() {
		return snVisualizar;
	}
	public void setSnVisualizar(Boolean snVisualizar) {
		this.snVisualizar = snVisualizar;
	}
					
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_permissao")		
	public Permissao getPermissao() {
		return permissao;
	}
	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_usuario")	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
		
	@Override
	public String toString() {
		return "UsuarioPermissao [idUsuarioPermissao=" + idUsuarioPermissao + ", snAdicionar=" + snAdicionar
				+ ", snEditar=" + snEditar + ", snRemover=" + snRemover + ", snVisualizar=" + snVisualizar + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuarioPermissao == null) ? 0 : idUsuarioPermissao.hashCode());
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
		UsuarioPermissao other = (UsuarioPermissao) obj;
		if (idUsuarioPermissao == null) {
			if (other.idUsuarioPermissao != null)
				return false;
		} else if (!idUsuarioPermissao.equals(other.idUsuarioPermissao))
			return false;
		return true;
	}		
}
